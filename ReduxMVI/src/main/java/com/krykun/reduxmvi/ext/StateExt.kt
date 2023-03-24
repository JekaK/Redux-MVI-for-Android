package com.krykun.reduxmvi.ext

import com.krykun.reduxmvi.global.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate

inline fun <reified T> HashSet<MutableStateFlow<Any>>.takeCurrent(): MutableStateFlow<T> {
    return this.find {
        it.value is T
    } as MutableStateFlow<T>
}

inline fun <reified T> AppState.applyForState(stateUpdate: (T) -> T): AppState {
    return this.copy(
        stateSet = this.stateSet.apply {
            this.takeCurrent<T>().getAndUpdate {
                stateUpdate(it)
            }
        })
}

inline fun <reified T> AppState.findStateFlow(): Flow<T> {
    return (this.stateSet.find {
        it.value is T
    } as MutableStateFlow<T>)
}

inline fun <reified T> AppState.findState(): T {
    return (this.stateSet.find {
        it.value is T
    } as MutableStateFlow<T>).value
}

