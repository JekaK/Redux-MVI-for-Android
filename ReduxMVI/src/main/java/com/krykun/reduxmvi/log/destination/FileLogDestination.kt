package com.krykun.reduxmvi.log.destination

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.krykun.reduxmvi.BuildConfig
import com.krykun.reduxmvi.log.Channel
import com.krykun.reduxmvi.log.Log
import com.krykun.reduxmvi.log.LogLevel
import com.krykun.reduxmvi.utils.DateUtils
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class FileLogDestination(
    dispatcher: CoroutineDispatcher,
    private val context: Context,
    private val minLogLevel: LogLevel,
    private val logBufferSize: Int,
    private val maxLogsFileAmount: Int
) : LogDestination {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            "LocalFileLogDestination",
            "IO error",
            throwable
        )
    }

    private val scope = CoroutineScope(SupervisorJob() + dispatcher + coroutineExceptionHandler)
    private val buffer = ArrayList<LogItem>()
    private val todayLogFileName: String by lazy {
        logFileNameForTime(System.currentTimeMillis())
    }

    init {
        scope.launch {
            deleteOutdatedLogFiles()
        }
    }

    override fun log(logLevel: LogLevel, tag: String, message: String, channel: Channel) {
        if (logLevel.priority < minLogLevel.priority) return
        buffer.add(
            LogItem(
                timestamp = System.currentTimeMillis(),
                logLevel = logLevel,
                tag = tag,
                message = message
            )
        )
        if (buffer.size >= logBufferSize) {
            // copy buffer and write it to file
            writeLogsToFile(ArrayList(buffer))
            buffer.clear()
        }
    }

    private fun logFileNameForTime(timestamp: Long): String {
        return "log_${DateUtils.dateFromTimestamp(timestamp, "dd-MM-yyyy")}.txt"
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private fun writeLogsToFile(items: List<LogItem>) {
        scope.launch {
            val logFile = getLogsFile()

            val outputStream = FileOutputStream(logFile, true)

            items.forEach {
                outputStream.write(it.toString().toByteArray())
            }

            outputStream.flush()
            outputStream.close()
        }
    }

    private fun getLogsFile(): File {
        val file = File(context.filesDir, "$logsDirName/$todayLogFileName")
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
        return file
    }

    private fun deleteOutdatedLogFiles() {
        val file = logsDirectory(context)
        if (file.exists() && file.isDirectory) {
            val files = file.listFiles()!!.toList()

            if (files.size > maxLogsFileAmount) {
                files.sortedByDescending { it.lastModified() }
                    .takeLast(files.size - maxLogsFileAmount)
                    .forEach {
                        it.delete()
                    }
            }
        }
    }

    private data class LogItem(
        val timestamp: Long,
        val logLevel: LogLevel,
        val tag: String,
        val message: String
    ) {
        private val timestampFormat = "yyyy-MM-dd HH:mm:ss.SSS"

        override fun toString(): String {
            return "${
                DateUtils.dateFromTimestamp(
                    timestamp,
                    timestampFormat
                )
            } $logLevel $tag: $message\n"
        }
    }

    companion object {
        private const val logsDirName = "logs"
        private const val fileProviderAuthority = BuildConfig.LIBRARY_PACKAGE_NAME + ".fileprovider"

        fun getLogFilesSharingUri(context: Context): List<Uri> {
            return logsDirectory(context).listFiles()!!.toList()
                .map { FileProvider.getUriForFile(context, fileProviderAuthority, it) }
        }

        private fun logsDirectory(context: Context): File {
            return File(context.filesDir, logsDirName)
        }
    }
}
