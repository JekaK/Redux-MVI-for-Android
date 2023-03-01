package com.krykun.reduxmvi.log.logger

import com.krykun.reduxmvi.log.Log

/**
 * Abstraction for the Log with tag per instance
 */
class Logger private constructor(private val tag: String) {
    fun d(message: String) {
        Log.d(tag, message)
    }

    fun v(message: String) {
        Log.v(tag, message)
    }

    fun i(message: String) {
        Log.i(tag, message)
    }

    fun w(message: String, error: Throwable? = null) {
        Log.w(tag, message, error)
    }

    fun e(message: String, error: Throwable? = null) {
        Log.e(tag, message, error)
    }

    companion object {
        fun create(x: Class<*>) = Logger(x.simpleName)
        fun create(tag: String) = Logger(tag)
    }
}
