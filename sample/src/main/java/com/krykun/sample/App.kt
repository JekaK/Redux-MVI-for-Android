package com.krykun.sample

import android.app.Application
import com.krykun.reduxmvi.di.dataModules
import com.krykun.reduxmvi.di.hardwareModules
import com.krykun.reduxmvi.di.presentationModules
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
                *hardwareModules,
                *dataModules,
                *presentationModules,
                *viewModelModule,
                *middlewareModule,
                *featureModule
            )
        }
    }
}