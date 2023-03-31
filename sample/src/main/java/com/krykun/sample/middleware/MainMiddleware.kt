package com.krykun.sample.middleware

import com.krykun.reduxmvi.ext.findState
import com.krykun.reduxmvi.global.*
import com.krykun.sample.action.AddCounterAction
import com.krykun.sample.action.ShowCounterToastAction
import com.krykun.sample.presentation.MainState

class MainMiddleware : Middleware<Action, Store<Action, AppState>> {

    /**
     * > When the `AddCounterAction` is dispatched, dispatch a `CounterToastAction`
     *
     * The `dispatchCounterToastAction` function is defined as follows:
     *
     * @param action The action that was dispatched.
     * @param store The store that the action was dispatched to.
     * @param next Dispatcher<Action, Store<Action, AppState>>
     * @return The next action
     */
    override fun dispatch(
        action: Action,
        store: Store<Action, AppState>,
        next: Dispatcher<Action, Store<Action, AppState>>,
    ): Action {

        val next = next.dispatch(action, store)

        when (action) {
            is AddCounterAction -> dispatchCounterToastAction(store)
        }

        return next
    }


    /**
     * "Dispatch a toast action that shows the current counter value."
     *
     * The function is private because it's only used internally by the MainActivity
     *
     * @param store Store<Action, AppState> - The store that the middleware is being applied to.
     */
    private fun dispatchCounterToastAction(store: Store<Action, AppState>) {
        val counter = store.getState().findState<MainState>().counter

        store.dispatch(ShowCounterToastAction(counter))
    }

}