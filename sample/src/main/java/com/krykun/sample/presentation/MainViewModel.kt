package com.krykun.sample.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krykun.reduxmvi.CleanupFeature
import com.krykun.reduxmvi.SetupFeature
import com.krykun.reduxmvi.action.AddStateAction
import com.krykun.reduxmvi.ext.getStateUpdatesMapped
import com.krykun.reduxmvi.ext.getStateUpdatesProperty
import com.krykun.reduxmvi.ext.toDedicatedType
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.navigation.navigationFlowOf
import com.krykun.sample.action.AddCounterAction
import com.krykun.sample.di.Feature
import com.krykun.sample.navigation.MainNavigation
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
    val navigationEventsState = mutableStateOf<MainNavigation>(MainNavigation.EmptyNavEvent)

    /* Creating a new instance of the MainState class and adding it to the store. */
    init {
        /* Adding the `MainState` to the store and setting up the `MainFeature` */
        store.dispatch(AddStateAction(MainState()))
        store.dispatch(SetupFeature(Feature.MAIN))
        /* Collecting the `Property<Int>` emitted by `propertyProps()` and setting the value of
        `counter` to the value emitted by `propertyProps()` */
        viewModelScope.launch {
            propertyProps()
                .collect { value ->
                    counter.value = value
                }
        }
        /* Collecting the `Flow<MainProps>` emitted by `mainProps()` and setting the value of
        `props` to the value emitted by `mainProps()` */
        viewModelScope.launch {
            mainProps().collect {
                props.value = it
            }
        }
        /* Collecting the `Flow<MainNavigation>` emitted by `navigationProps()` and setting the value
        of
                `navigationEventsState` to the value emitted by `navigationProps()` */
        viewModelScope.launch {
            navigationProps().collect {
                when (it.navigationRequest) {
                    is MainNavigation.EmptyNavEvent -> {}
                    else -> {
                        navigationEventsState.value = it.navigationRequest
                    }
                }
                it.consumed()
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
        .getStateUpdatesProperty<MainState>(bindingDispatcher) {
            it.counter
        }
        .toDedicatedType<Int>()


    /**
     * It returns a Flow of MainNavigation objects that are emitted whenever the navigation state
     * changes
     */
    private fun navigationProps() = store.navigationFlowOf<MainNavigation>(bindingDispatcher)

    /**
     * > When the view model is destroyed, dispatch a cleanup action to the store
     */
    override fun onCleared() {
        store.dispatch(CleanupFeature(Feature.MAIN))
        super.onCleared()
    }
}