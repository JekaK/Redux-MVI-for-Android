package com.krykun.reduxmvi.action

import com.krykun.reduxmvi.global.AppState

class SetupStateAction(private val appViewState: Any) : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(
            viewState = appViewState
        )
    }
}