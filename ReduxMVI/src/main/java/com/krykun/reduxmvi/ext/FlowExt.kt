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

/**
 * Transforms a Flow of [AppState] into a Flow of a different type [R] by observing changes in the state
 * and extracting the [ViewState] contained in the [AppState]. This function is useful for mapping
 * from the overall application state to a specific view state.
 *
 * @param transform A transformation function that takes a value of type [T] extracted from the [ViewState]
 * and produces a value of type [R].
 * @return A Flow of [R] representing the transformed view state.
 */
inline fun <T, R> Flow<AppState>.takeWhenChangedAsViewState(crossinline transform: suspend (value: T) -> R): Flow<R> =
    this.takeWhenChanged<AppState, T> {
        it.viewState.asViewState()
    }.takeWhenChanged(transform)

