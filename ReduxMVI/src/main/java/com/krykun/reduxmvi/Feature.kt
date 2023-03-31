package com.krykun.reduxmvi

import com.krykun.reduxmvi.global.Action

abstract class Feature {
    object GLOBAL : Feature()
}

data class SetupFeature(val feature: Feature) : Action

data class CleanupFeature(val feature: Feature) : Action
