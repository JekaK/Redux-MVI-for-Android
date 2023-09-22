package com.krykun.sample

import android.app.Application
import com.krykun.reduxmvi.action.SetupStateAction
import com.krykun.reduxmvi.di.listOfModules
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.sample.di.featureModule
import com.krykun.sample.di.middlewareModule
import com.krykun.sample.di.viewModelModule
import com.krykun.sample.state.ViewState
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDIGraph()
    }

    private fun setupDIGraph() {
        startKoin {
            androidContext(this@App)
            modules(
                *listOfModules,
                *viewModelModule,
                *middlewareModule,
                *featureModule
            )
        }
        val store: Store<Action, AppState> = get()
        store.dispatch(SetupStateAction(ViewState()))
    }
}