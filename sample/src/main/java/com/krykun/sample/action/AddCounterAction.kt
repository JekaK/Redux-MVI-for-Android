package com.krykun.sample.action

import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.ext.updateViewState
import com.krykun.reduxmvi.global.AppState
import com.krykun.sample.state.ViewState

class AddCounterAction : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(
            viewState = state.viewState.updateViewState<ViewState> {
                it.copy(
                    mainState = it.mainState.copy(
                        counter = it.mainState.counter + 1
                    )
                )
            }
        )
    }
}