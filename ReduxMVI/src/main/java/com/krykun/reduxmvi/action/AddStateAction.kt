package com.krykun.reduxmvi.action

import com.krykun.reduxmvi.global.AppState
import kotlinx.coroutines.flow.MutableStateFlow

class AddStateAction(
    private val state: Any
) : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(
            stateSet = state.stateSet.apply {
                add(MutableStateFlow(this@AddStateAction.state))
            }
        )
    }
}