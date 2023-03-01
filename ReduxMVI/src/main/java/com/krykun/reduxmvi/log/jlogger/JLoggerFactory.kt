package com.krykun.reduxmvi.log.jlogger

class JLoggerFactory private constructor() {
    init {
        throw IllegalStateException("JLoggerFactory class")
    }

    companion object {
        fun getLogger(clazz: Class<*>): JLogger {
            return JLogger(clazz.simpleName)
        }
    }
}