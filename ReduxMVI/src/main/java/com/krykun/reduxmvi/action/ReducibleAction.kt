package com.krykun.reduxmvi.action

import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState

interface ReducibleAction : Action {

    fun reduce(state: AppState): AppState
}
