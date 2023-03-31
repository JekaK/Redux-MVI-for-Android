package com.krykun.sample

import android.app.Application
import com.krykun.reduxmvi.di.listOfModules
import com.krykun.sample.di.featureModule
import com.krykun.sample.di.middlewareModule
import com.krykun.sample.di.viewModelModule
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
    }
}