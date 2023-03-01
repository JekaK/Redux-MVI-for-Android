package com.krykun.reduxmvi.log.destination

interface ServerLogDestination : LogDestination {

    /**
     * Send all collected logs immediately
     */
    fun purgeLogs()
}
