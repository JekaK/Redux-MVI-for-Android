package com.krykun.reduxmvi.di

import com.krykun.reduxmvi.device.DeviceInfoProvider
import com.krykun.reduxmvi.device.DeviceInfoProviderImpl
import org.koin.dsl.module

private val deviceModule = module {
    factory<DeviceInfoProvider> { DeviceInfoProviderImpl() }
}

val hardwareModules = arrayOf( deviceModule)
