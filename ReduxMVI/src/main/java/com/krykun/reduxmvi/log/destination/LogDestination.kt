package com.krykun.reduxmvi.log.destination

import com.krykun.reduxmvi.log.Channel
import com.krykun.reduxmvi.log.LogLevel

interface LogDestination {

    fun log(logLevel: LogLevel, tag: String, message: String, channel: Channel)
}
