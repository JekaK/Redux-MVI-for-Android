package com.krykun.reduxmvi.device

interface DeviceInfoProvider {

    fun getOsDescription(): String

    fun getDeviceModelDescription(): String

    fun sdkVersion(): String
}
