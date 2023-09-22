package com.krykun.reduxmvi.action

import com.krykun.reduxmvi.global.AppState

/**
 * A class representing an action to set up the application's state with a specified view state.
 * This action is used to update the [AppState] by replacing its view state with a new one.
 *
 * @param appViewState The new view state to set up in the application's state.
 */
class SetupStateAction(private val appViewState: Any) : ReducibleAction {
    /**
     * Reduces the current application state by replacing the existing view state with the provided one.
     *
     * @param state The current [AppState] to be modified.
     * @return The modified [AppState] with the updated view state.
     */
    override fun reduce(state: AppState): AppState {
        return state.copy(
            viewState = appViewState
        )
    }
}