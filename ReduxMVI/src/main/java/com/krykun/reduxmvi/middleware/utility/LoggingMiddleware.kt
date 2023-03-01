package com.krykun.reduxmvi.middleware.utility

import com.krykun.reduxmvi.action.ReducibleAction
import com.krykun.reduxmvi.action.StoreInitialized
import com.krykun.reduxmvi.device.DeviceInfoProvider
import com.krykun.reduxmvi.global.*
import com.krykun.reduxmvi.log.Log
import com.krykun.reduxmvi.log.destination.ServerLogDestination
import com.krykun.reduxmvi.utils.StateDiffLogger

/**
 * Middleware for logging actions that are going through
 */
class LoggingMiddleware(
    private val deviceInfoProvider: DeviceInfoProvider,
    private val serverLogDestination: ServerLogDestination,
    private val stateDiffLogger: StateDiffLogger,
) :
    Middleware<Action, Store<Action, AppState>> {

    override fun dispatch(
        action: Action,
        store: Store<Action, AppState>,
        next: Dispatcher<Action, Store<Action, AppState>>
    ): Action {

        when (action) {
            is StoreInitialized -> logDeviceDetails()
        }

        Log.i("ReduxDispatch", "Action dispatched: $action")
        val oldState = store.getState()
        val reduced = next.dispatch(action, store)
        if (action is ReducibleAction) {
            stateDiffLogger.printDiff(oldState, store.getState())
        }
        return reduced
    }

    private fun logDeviceDetails() {
        Log.i("DeviceInfo", "Device model ${deviceInfoProvider.getDeviceModelDescription()}")
        Log.i("DeviceInfo", "OS ${deviceInfoProvider.getOsDescription()}")
    }
}
