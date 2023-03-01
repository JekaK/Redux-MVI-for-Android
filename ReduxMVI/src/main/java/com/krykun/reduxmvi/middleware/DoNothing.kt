package com.krykun.reduxmvi.middleware

import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Dispatcher
import com.krykun.reduxmvi.global.Store

object DoNothing : Dispatcher<Action, Store<Action, AppState>> {
    override fun dispatch(action: Action, store: Store<Action, AppState>): Action {
        return action
    }
}
