package com.krykun.reduxmvi.middleware

import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.Middleware
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Dispatcher

/**
 * Delegate dispatching to the inner middleware and holds a link to the next dispatcher in the chain.
 */
data class DispatcherNode(
    private val middleware: Middleware<Action, Store<Action, AppState>>,
    private val next: Dispatcher<Action, Store<Action, AppState>> = DoNothing
) : Dispatcher<Action, Store<Action, AppState>> {

    override fun dispatch(action: Action, store: Store<Action, AppState>): Action {
        return middleware.dispatch(action, store, next)
    }
}
