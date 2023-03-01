package com.krykun.reduxmvi.log.destination

import com.krykun.reduxmvi.log.Channel
import com.krykun.reduxmvi.log.Log
import com.krykun.reduxmvi.log.LogLevel
import com.krykun.reduxmvi.log.model.ErrorLogDetails
import com.krykun.reduxmvi.utils.Constants
import kotlinx.coroutines.*
import org.joda.time.DateTime
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

class ApiLogDestination(
    private val minLogLevel: LogLevel,
    private val logSendingIntervalSeconds: Int,
) : ServerLogDestination {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            "ApiLogDestination",
            "ApiLogDestination error",
            throwable
        )
    }

    private val scope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)
    private val buffer = ArrayList<ErrorLogDetails>()
    private var debounceJob: Job? = null

    @ExperimentalTime
    override fun log(logLevel: LogLevel, tag: String, message: String, channel: Channel) {
        // ignore logs that have lower priority
        if (logLevel.priority < minLogLevel.priority) return
        scope.launch {
            buffer.add(
                ErrorLogDetails(
                    clientTime = DateTime().toString(),
                    level = logLevel.name,
                    source = Constants.ERROR_LOG_SOURCE,
                    message = buildMessage(tag, message, channel)
                )
            )
            sendLogsDebounced()
        }
    }

    override fun purgeLogs() {
        if (buffer.isNotEmpty()) {
            buffer.clear()
        }
    }

    @ExperimentalTime
    private fun sendLogsDebounced() {
        if (debounceJob == null) {
            debounceJob = scope.launch {
                while (buffer.isNotEmpty()) {
                    purgeLogs()
                    delay(logSendingIntervalSeconds.milliseconds)
                }
                debounceJob = null
            }
        }
    }

    private fun buildMessage(tag: String, message: String, channel: Channel): String {
        val builder = StringBuilder()
        builder.append("TAG:$tag")
        builder.append(" | CHANNEL: $channel")
        if (message.isNotBlank()) {
            builder.append(" | MESSAGE: $message")
        }
        return builder.toString()
    }
}
