package com.krykun.reduxmvi.middleware

import com.krykun.reduxmvi.Feature
import com.krykun.reduxmvi.global.Middleware

interface MiddlewareFactory<A, S> {
    fun createForFeature(feature: Feature): List<Middleware<A, S>>
}
