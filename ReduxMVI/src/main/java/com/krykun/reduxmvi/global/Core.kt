package com.krykun.reduxmvi.global

import kotlinx.coroutines.flow.Flow

/**
 * Represent a pure function that create new state based on
 * action and state that system have right now.
 * Should not produce any side effects
 */
interface Reducer<A, S> {
    fun reduce(action: A, state: S): S
}

/**
 * Represent a side effects handler.
 */
interface Middleware<A, S> {
    fun dispatch(action: A, store: S, next: Dispatcher<A, S>): A
}

/**
 * Generic interface for a dispatcher.
 */
interface Dispatcher<A, S> {
    fun dispatch(action: A, store: S): A
}

/**
 * Represents a read only provider for a current app state.
 */
interface StateProvider<S> {
    fun getState(): S
}

/**
 * Represents a state store, connect actions with middleware and reducers for state production.
 */
interface Store<A, S> : StateProvider<S> {
    fun dispatch(action: A)
    fun stateFlow(): Flow<S>
}
