package com.krykun.reduxmvi.ext

/**
 * Casts the current object to a specified type [T] and returns it.
 * This function is used for type casting when it is known that the object represents
 * a view state of type [T].
 *
 * @return The object casted to the specified type [T].
 * @throws ClassCastException if the object cannot be cast to type [T].
 */
fun <T> Any.asViewState(): T {
    return this as T
}

/**
 * Updates a view state object of type [T] by applying a transformation [block] to it.
 * This function is designed for modifying view state objects in a type-safe manner.
 *
 * @param block A transformation function that takes the current view state of type [T] and returns a modified view state of the same type.
 * @return The updated view state of type [T].
 */
inline fun <reified T : Any> Any.updateViewState(block: (T) -> T): T {
    return this.asViewState<T>().run(block)
}
