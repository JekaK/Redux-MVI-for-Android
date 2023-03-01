package com.krykun.reduxmvi.global

import android.util.Log
import com.krykun.reduxmvi.action.StoreInitialized
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Global Redux store for the app.
 */
class AppStore(
    private val reducer: Reducer<Action, AppState>,
    private val middleware: Middleware<Action, Store<Action, AppState>>,
    dispatcher: CoroutineDispatcher
) : Store<Action, AppState> {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        Log.e(
            "AppStore",
            "Error in AppStore coroutine scope\n${getState()}",
            throwable
        )
    }
    private val scope = CoroutineScope(SupervisorJob() + dispatcher + coroutineExceptionHandler)

    private val stateChannel = MutableStateFlow(AppState())

    private val endOfTheChain = object : Dispatcher<Action, Store<Action, AppState>> {
        override fun dispatch(
            action: Action,
            store: Store<Action, AppState>
        ): Action {
            stateChannel.value = reducer.reduce(action, getState())
            return action
        }
    }

    init {
        dispatch(StoreInitialized)
    }

    override fun dispatch(action: Action) {
        scope.launch {
            middleware.dispatch(action, this@AppStore, endOfTheChain)
        }
    }

    override fun getState() = stateChannel.value

    override fun stateFlow(): Flow<AppState> {
        return stateChannel
    }
}
