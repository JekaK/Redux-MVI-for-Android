package com.krykun.sample.presentation

data class MainProps(
    val counter: Int = 0,
    val addCounterAction: () -> Unit = {}
)