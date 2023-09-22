package com.krykun.reduxmvi.ext

import com.krykun.reduxmvi.global.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Returns a flow containing the results of applying the given [transform] function to each value of the original flow.
 * Apply [distinctUntilChanged] to ignore equal values that are coming in a row
 */
inline fun <T, R> Flow<T>.takeWhenChanged(crossinline transform: suspend (value: T) -> R): Flow<R> =
    map(transform).distinctUntilChanged()


inline fun <T, R> Flow<AppState>.takeWhenChangedAsViewState(crossinline transform: suspend (value: T) -> R): Flow<R> =
    this.takeWhenChanged<AppState, T> {
        it.viewState.asViewState()
    }.takeWhenChanged(transform)

