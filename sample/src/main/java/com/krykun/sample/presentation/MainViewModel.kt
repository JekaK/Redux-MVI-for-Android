package com.krykun.sample.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krykun.reduxmvi.CleanupFeature
import com.krykun.reduxmvi.SetupFeature
import com.krykun.reduxmvi.ext.takeWhenChangedAsViewState
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.navigation.navigationFlowOf
import com.krykun.sample.action.AddCounterAction
import com.krykun.sample.di.Feature
import com.krykun.sample.navigation.MainNavigation
import com.krykun.sample.state.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    val navigationEventsState = mutableStateOf<MainNavigation>(MainNavigation.EmptyNavEvent)

    /* Creating a new instance of the MainState class and adding it to the store. */
    init {
        store.dispatch(SetupFeature(Feature.MAIN))
        /* Collecting the `Flow<MainNavigation>` emitted by `navigationProps()` and setting the value
        of `navigationEventsState` to the value emitted by `navigationProps()` */
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

    fun addCounter() {
        store.dispatch(AddCounterAction())
    }

    /**
     * Constructs a Flow of [MainProps] representing the main properties of the application's user interface.
     * This function observes changes in the application's state, extracts the relevant [MainState], and maps it
     * to a [MainProps] object with specific properties.
     *
     * @return A Flow of [MainProps] containing the main properties for the user interface.
     */
    fun mainProps() = store.stateFlow()
        .takeWhenChangedAsViewState<ViewState, MainState> {
            it.mainState
        }
        .map {
            MainProps(it.counter, this::addCounter)
        }
        .flowOn(bindingDispatcher)

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