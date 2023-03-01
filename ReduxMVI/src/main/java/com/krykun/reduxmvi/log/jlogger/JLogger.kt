package com.krykun.reduxmvi.log.jlogger

import com.krykun.reduxmvi.log.Log

/**
 * Abstraction for the Log usage from java with api similar to the old org.slf4j logger we used
 */
class JLogger(private val tag: String) {
    fun debug(message: String?) {
        Log.d(tag, message ?: "")
    }

    fun verbose(message: String?) {
        Log.v(tag, message ?: "")
    }

    fun info(message: String?) {
        Log.i(tag, message ?: "")
    }

    fun warn(message: String?) {
        Log.w(tag, message ?: "")
    }

    fun warn(message: String?, error: Throwable?) {
        Log.w(tag, message ?: "", error)
    }

    fun error(message: String?) {
        Log.e(tag, message ?: "")
    }

    fun error(message: String?, error: Throwable?) {
        Log.e(tag, message ?: "", error)
    }
}
