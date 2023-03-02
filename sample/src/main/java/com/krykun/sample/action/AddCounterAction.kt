package com.krykun.sample.action

import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.ext.takeCurrent
import com.krykun.reduxmvi.global.AppState
import com.krykun.sample.presentation.MainState
import kotlinx.coroutines.flow.getAndUpdate

class AddCounterAction : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(
            stateSet = state.stateSet.apply {
                this.takeCurrent<MainState>().getAndUpdate {
                    it.copy(counter = it.counter + 1)
                }
            }
        )
    }
}