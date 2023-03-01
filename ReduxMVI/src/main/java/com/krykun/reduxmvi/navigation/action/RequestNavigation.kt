package com.krykun.reduxmvi.navigation.action

import android.util.Log
import com.krykun.reduxmvi.navigation.NavigationRequest
import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.global.AppState

/**
 * Action for adding [NavigationRequest] to the list of requests in state
 * Could be used directly or subclassed
 * When requesting navigation make sure that there is a consume logic added in UI/presentation layer
 */
open class RequestNavigation(val request: NavigationRequest) : ReducibleAction {
    override fun reduce(state: AppState): AppState {
        // Allow only unique requests in the state. If request already present log warning
        return if (state.navigationRequests.contains(request)) {
            Log.w(
                "RequestNavigation",
                "$request is ignored because same request is not consumed yet"
            )
            state
        } else {
            state.copy(navigationRequests = state.navigationRequests + request)
        }
    }
}
