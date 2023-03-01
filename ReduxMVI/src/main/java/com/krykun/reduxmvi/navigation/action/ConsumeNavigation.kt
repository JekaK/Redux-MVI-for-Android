package com.krykun.reduxmvi.navigation.action

import com.krykun.reduxmvi.navigation.NavigationRequest
import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.global.AppState

/**
 * Action for removing consumed [NavigationRequest] from state
 * Could be used directly or subclassed
 * Instances of this action must be dispatched after navigation request is handled by the UI layer
 */
data class ConsumeNavigation(val request: NavigationRequest) : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(navigationRequests = state.navigationRequests - request)
    }
}
