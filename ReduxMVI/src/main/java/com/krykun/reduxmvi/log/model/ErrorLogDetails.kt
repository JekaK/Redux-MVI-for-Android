package com.krykun.reduxmvi.log.model

data class ErrorLogDetails @JvmOverloads constructor(
    var clientTime: String? = null,
    var level: String? = null,
    var source: String? = null,
    var tracking: String? = null,
    var message: String? = null,
    var code: String? = null,
    var data: String? = null
)
