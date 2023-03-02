package com.krykun.sample.presentation

import androidx.lifecycle.ViewModel
import com.krykun.reduxmvi.action.AddStateAction
import com.krykun.reduxmvi.ext.getStateUpdates
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.sample.action.AddCounterAction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val store: Store<Action, AppState>,
    private val bindingDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        store.dispatch(AddStateAction(MainState()))
    }

    fun mainProps() = store.stateFlow()
        .getStateUpdates<MainState>()
        .map {
            MainProps(it.counter) {
                store.dispatch(AddCounterAction())
            }
        }
        .flowOn(bindingDispatcher)
}