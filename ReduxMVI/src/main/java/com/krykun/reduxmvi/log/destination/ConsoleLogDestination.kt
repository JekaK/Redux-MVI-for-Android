package com.krykun.reduxmvi.log.destination

import android.util.Log
import com.krykun.reduxmvi.log.Channel
import com.krykun.reduxmvi.log.LogLevel
import kotlin.math.min

class ConsoleLogDestination(
    private val minLogLevel: LogLevel = LogLevel.INFO
) : LogDestination {

    // max logcat line length
    private val maxLogLineLength = 2000

    override fun log(logLevel: LogLevel, tag: String, message: String, channel: Channel) {
        if (logLevel.priority < minLogLevel.priority) return
        when (logLevel) {
            LogLevel.DEBUG -> splitLines(message) { Log.d(tag, it) }
            LogLevel.VERBOSE -> splitLines(message) { Log.v(tag, it) }
            LogLevel.INFO -> splitLines(message) { Log.i(tag, it) }
            LogLevel.WARN -> splitLines(message) { Log.w(tag, it) }
            LogLevel.ERROR -> splitLines(message) { Log.e(tag, it) }
            else -> {}
        }
    }

    private fun splitLines(message: String, logLine: (String) -> Unit) {
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = min(newline, i + maxLogLineLength)
                val part = message.substring(i, end)
                logLine(part)
                i = end
            } while (i < newline)
            i++
        }
    }
}
