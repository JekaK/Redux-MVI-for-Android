package com.krykun.reduxmvi.middleware

import com.krykun.reduxmvi.CleanupFeature
import com.krykun.reduxmvi.Feature
import com.krykun.reduxmvi.SetupFeature
import com.krykun.reduxmvi.global.*
import com.krykun.reduxmvi.log.jlogger.JLoggerFactory

/**
 * Manages feature middleware creation and cleanup. Ensure inner middleware execution of dispatched actions
 */
class CompositeMiddleware(
    private val middlewareFactory: MiddlewareFactory<Action, Store<Action, AppState>>
) : Middleware<Action, Store<Action, AppState>> {

    private val log = JLoggerFactory.getLogger(CompositeMiddleware::class.java)
    private val middleware = HashMap<Feature, List<Middleware<Action, Store<Action, AppState>>>>()

    init {
        addForFeature(Feature.GLOBAL)
    }

    override fun dispatch(
        action: Action,
        store: Store<Action, AppState>,
        next: Dispatcher<Action, Store<Action, AppState>>
    ): Action {
        // setup or cleanup feature, do not pass action any further because it is handled here
        return when (action) {
            is SetupFeature -> {
                addForFeature(action.feature)
                action
            }
            is CleanupFeature -> {
                cleanupFeature(action.feature)
                action
            }
            else -> {
                dispatchToInnerMiddleware(action, store, next)
            }
        }
    }

    private fun dispatchToInnerMiddleware(
        action: Action,
        store: Store<Action, AppState>,
        next: Dispatcher<Action, Store<Action, AppState>>
    ): Action {
        val allMiddleware = middleware.values.flatten()
        // there is nothing to do if there is no active middleware
        if (allMiddleware.isEmpty()) return action
        // for all active middleware create a connected nodes starting
        // from the end of the list with incoming next
        var lastNode = DispatcherNode(allMiddleware.last(), next)
        for (i in allMiddleware.lastIndex - 1 downTo 0) {
            lastNode = DispatcherNode(allMiddleware[i], lastNode)
        }
        return lastNode.dispatch(action, store)
    }

    private fun addForFeature(feature: Feature) {
        middleware[feature] = middlewareFactory.createForFeature(feature)
        log.debug("Added nodes for feature $feature")
    }

    private fun cleanupFeature(feature: Feature) {
        middleware.remove(feature)
        log.debug("Feature cleaned up $feature")
    }
}
