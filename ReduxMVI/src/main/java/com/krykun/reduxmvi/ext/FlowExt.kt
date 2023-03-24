package com.krykun.reduxmvi.ext

import android.util.Log
import com.krykun.reduxmvi.global.AppState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Returns a flow containing the results of applying the given [transform] function to each value of the original flow.
 * Apply [distinctUntilChanged] to ignore equal values that are coming in a row
 */
inline fun <T, R> Flow<T>.takeWhenChanged(crossinline transform: suspend (value: T) -> R): Flow<R> =
    map(transform).distinctUntilChanged()


inline fun <reified T> Flow<AppState>.getStateUpdates(bindingDispatcher: CoroutineDispatcher): Flow<T> {
    return this
        .takeWhenChanged<AppState, T> { it.findState() }
        .flowOn(bindingDispatcher)
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T, reified R> Flow<AppState>.getStateUpdatesProperty(
    bindingDispatcher: CoroutineDispatcher,
    crossinline transform: suspend (value: T) -> R,
): Flow<R> {

    return this
        .flatMapLatest {
            it.findStateFlow<T>().takeWhenChanged(transform)
        }
        .flowOn(bindingDispatcher)
}

inline fun <reified T, reified S> Flow<AppState>.getStateUpdatesMapped(
    bindingDispatcher: CoroutineDispatcher,
    crossinline map: (T) -> S,
): Flow<S> {
    return this
        .takeWhenChanged<AppState, T> { it.findState() }
        .map { map(it) }
        .flowOn(bindingDispatcher)
}


