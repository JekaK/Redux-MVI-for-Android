package com.krykun.reduxmvi.di

import com.krykun.reduxmvi.Feature
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.AppStore
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.middleware.CompositeMiddleware
import com.krykun.reduxmvi.middleware.MiddlewareFactory
import com.krykun.reduxmvi.middleware.utility.LoggingMiddleware
import com.krykun.reduxmvi.reducer.CompositeReducer
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val reducerModule = module {
    factory { CompositeReducer() }
}

private val middlewareModule = module {
    factory { LoggingMiddleware(get(), get(), get()) }
    single { CompositeMiddleware(get()) }
}

private val featuresModule = module {
    factory(named(Feature.GLOBAL.name)) {
        listOf(
            get<LoggingMiddleware>(),
        )
    }

    single<MiddlewareFactory<Action, Store<Action, AppState>>> {
        KoinMiddlewareFactory(
            androidApplication()
        )
    }
}

private val reduxModule = module {
    single<Store<Action, AppState>> {
        val store = AppStore(
            reducer = get<CompositeReducer>(),
            middleware = get<CompositeMiddleware>(),
            dispatcher = get(named(NEW_SINGLE_THREAD_DISPATCHER))
        )

        store
    }
}

val presentationModules = arrayOf(
    reducerModule,
    middlewareModule,
    featuresModule,
    reduxModule,
)
