package com.krykun.sample.di

import com.krykun.sample.middleware.MainMiddleware
import org.koin.core.qualifier.named
import org.koin.dsl.module

sealed class Feature : com.krykun.reduxmvi.Feature() {
    object MAIN : Feature()
}

val featureModule = arrayOf(module {
    factory(named(Feature.MAIN::class.java.simpleName)) {
        listOf(get<MainMiddleware>())
    }
})