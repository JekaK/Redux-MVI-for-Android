package com.krykun.reduxmvi.di

import android.app.Application
import com.krykun.reduxmvi.Feature
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Middleware
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.middleware.MiddlewareFactory
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class KoinMiddlewareFactory(private val app: Application) :
    MiddlewareFactory<Action, Store<Action, AppState>> {

    override fun createForFeature(feature: Feature): List<Middleware<Action, Store<Action, AppState>>> {
        return app.get(named(feature::class.java.simpleName))
    }
}
