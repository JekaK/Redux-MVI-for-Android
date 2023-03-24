package com.krykun.sample.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krykun.reduxmvi.action.AddStateAction
import com.krykun.reduxmvi.ext.getStateUpdatesMapped
import com.krykun.reduxmvi.ext.getStateUpdatesProperty
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.sample.action.AddCounterAction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * `getStateUpdatesMapped` takes a `State` and returns a `Flow<Props>`
 *
 * The `getStateUpdatesMapped` function takes a `State` and returns a `Flow<Props>`. The `State` is
 * mapped to a `Props` object. The `Props` object is then emitted as a `Flow<Props>`
 */
class MainViewModel(
    private val store: Store<Action, AppState>,
    private val bindingDispatcher: CoroutineDispatcher,
) : ViewModel() {

    var counter = mutableStateOf(0)
    val props = mutableStateOf(MainProps())

    /* Creating a new instance of the MainState class and adding it to the store. */
    init {
        store.dispatch(AddStateAction(MainState()))
        viewModelScope.launch {
            propertyProps()
                .collect { value ->
                    counter.value = value
                }
        }
        viewModelScope.launch {
            mainProps().collect {
                props.value = it
            }
        }
    }

    /**
     * `mainProps` is a function that returns a `Flow` of `MainProps` that is updated whenever the
     * `MainState` changes
     */
    private fun mainProps() = store.stateFlow()
        .getStateUpdatesMapped<MainState, MainProps>(bindingDispatcher) {
            it.toProps {
                store.dispatch(AddCounterAction())
            }
        }

    /**
     * `propertyProps()` returns a `Property<Int>` that emits the current value of `MainState.counter`
     * whenever it changes
     */
    private fun propertyProps() = store.stateFlow()
        .getStateUpdatesProperty<MainState, Int>(bindingDispatcher) {
            it.counter
        }
}