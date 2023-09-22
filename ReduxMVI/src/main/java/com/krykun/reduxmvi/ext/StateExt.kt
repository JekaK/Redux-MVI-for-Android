package com.krykun.reduxmvi.ext

fun <T> Any.asViewState(): T {
    return this as T
}

inline fun <reified T : Any> Any.updateViewState(block: (T) -> T): T {
    return this.asViewState<T>().run(block)
}