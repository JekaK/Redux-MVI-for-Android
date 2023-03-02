package com.krykun.reduxmvi.ext

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <reified T> HashSet<MutableStateFlow<Any>>.takeCurrent(): MutableStateFlow<T> {
    return this.find {
        it.value is T
    } as MutableStateFlow<T>
}