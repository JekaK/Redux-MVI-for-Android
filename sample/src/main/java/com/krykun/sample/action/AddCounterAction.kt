package com.krykun.sample.action

import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.ext.applyForState
import com.krykun.reduxmvi.global.AppState
import com.krykun.sample.presentation.MainState

class AddCounterAction : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.applyForState<MainState> {
            it.copy(counter = (it.counter?:0) + 1)
        }
    }
}