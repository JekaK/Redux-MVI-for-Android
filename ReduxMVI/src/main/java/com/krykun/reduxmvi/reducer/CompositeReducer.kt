package com.krykun.reduxmvi.reducer

import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.action.StoreInitialized
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Reducer

/**
 * Main reducer, that handle links to all other reducers and knows the relation between action type
 * and reducer type that should handle it.
 */
class CompositeReducer : Reducer<Action, AppState> {

    override fun reduce(action: Action, state: AppState): AppState {
        return when (action) {
            is ReducibleAction -> action.reduce(state)
            is StoreInitialized -> state
            else -> state
        }
    }
}
