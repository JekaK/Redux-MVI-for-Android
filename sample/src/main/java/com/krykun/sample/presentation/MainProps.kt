package com.krykun.sample.presentation

data class MainProps(
    val counter: Int = 0,
    val addCounterAction: () -> Unit = {},
)

fun MainState.toProps(addCounterAction: () -> Unit): MainProps {
    return MainProps(this.counter ?: 0) {
        addCounterAction()
    }
}