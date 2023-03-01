package com.krykun.reduxmvi.device

import android.os.Build

class DeviceInfoProviderImpl : DeviceInfoProvider {

    override fun getOsDescription(): String {
        return "Android " + Build.VERSION.RELEASE
    }

    override fun getDeviceModelDescription(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    override fun sdkVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }
}
