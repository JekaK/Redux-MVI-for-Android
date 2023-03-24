package com.krykun.reduxmvi.log

import com.krykun.reduxmvilib.BuildConfig

object LogConfig {
    const val FILE_OUTPUT_BUFFER_SIZE = 5
    const val FILE_OUTPUT_MAX_LOG_FILES_COUNT = 10

    const val API_LOG_SENDING_INTERVAL_SECONDS = 10

    private val CONSOLE_OVERRIDE_LEVEL: LogLevel? = null
    private val FILE_OVERRIDE_LEVEL: LogLevel? = null
    private val API_OVERRIDE_LEVEL: LogLevel? = null

    fun getConsoleLevel(devSettingsOverride: LogLevel?): LogLevel {
        return devSettingsOverride ?: CONSOLE_OVERRIDE_LEVEL
        ?: if (BuildConfig.DEBUG) LogLevel.DEBUG else LogLevel.INFO
    }

    fun getFileLevel(devSettingsOverride: LogLevel?): LogLevel {
        return devSettingsOverride ?: FILE_OVERRIDE_LEVEL ?: LogLevel.INFO
    }

    fun getApiLevel(devSettingsOverride: LogLevel?): LogLevel {
        return devSettingsOverride ?: API_OVERRIDE_LEVEL
        ?: if (BuildConfig.DEBUG) LogLevel.NO_LOGS else LogLevel.WARN
    }

    fun getOverallMinLogLevel(devSettingsOverride: LogLevel?): LogLevel {
        var min = getConsoleLevel(devSettingsOverride)
        getFileLevel(devSettingsOverride).let { if (it.priority < min.priority) min = it }
        getApiLevel(devSettingsOverride).let { if (it.priority < min.priority) min = it }
        return min
    }
}
