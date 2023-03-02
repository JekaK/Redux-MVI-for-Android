package com.krykun.reduxmvi.action

import com.krykun.reduxmvi.global.AppState

class AddStateAction(
    private val state: Any
) : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(
            stateSet = state.stateSet.apply {
                add(this@AddStateAction.state)
            }
        )
    }
}