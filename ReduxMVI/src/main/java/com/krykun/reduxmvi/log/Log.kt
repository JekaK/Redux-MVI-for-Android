package com.krykun.reduxmvi.log

import com.krykun.reduxmvi.log.destination.LogDestination

object Log {

    private val logDestinations = ArrayList<LogDestination>()

    @SuppressWarnings("MemberNameEqualsClassName")
    private fun log(level: LogLevel, tag: String, message: String, channel: Channel) {
        logDestinations.forEach {
            it.log(level, tag, message, channel)
        }
    }

    @JvmOverloads
    fun d(tag: String, message: String, channel: Channel = Channel.APP) {
        log(LogLevel.DEBUG, tag, message, channel)
    }

    @JvmOverloads
    fun v(tag: String, message: String, channel: Channel = Channel.APP) {
        log(LogLevel.VERBOSE, tag, message, channel)
    }

    @JvmOverloads
    fun i(tag: String, message: String, channel: Channel = Channel.APP) {
        log(LogLevel.INFO, tag, message, channel)
    }

    @JvmOverloads
    fun w(tag: String, message: String, error: Throwable? = null, channel: Channel = Channel.APP) {
        val warningMessage =
            if (error != null) "$message${System.lineSeparator()}${error.stackTraceToString()}" else message
        log(LogLevel.WARN, tag, warningMessage, channel)
    }

    @JvmOverloads
    fun e(tag: String, message: String, error: Throwable? = null, channel: Channel = Channel.APP) {
        val errorMessage =
            if (error != null) "$message${System.lineSeparator()}${error.stackTraceToString()}" else message
        log(LogLevel.ERROR, tag, errorMessage, channel)
    }

    fun addLogDestination(logDestination: LogDestination) {
        logDestinations += logDestination
    }
}

enum class LogLevel(val priority: Int) {
    DEBUG(1),
    VERBOSE(2),
    INFO(3),
    WARN(4),
    ERROR(5),
    NO_LOGS(Int.MAX_VALUE);

    companion object {
        fun from(priority: Int): LogLevel? {
            return values().firstOrNull { it.priority == priority }
        }
    }
}

enum class Channel {
    APP
}
