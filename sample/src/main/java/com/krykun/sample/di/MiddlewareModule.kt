package com.krykun.sample.di

import com.krykun.sample.middleware.MainMiddleware
import org.koin.dsl.module

val middlewareModule = arrayOf(module {
    factory {
        MainMiddleware()
    }
})