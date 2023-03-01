package com.krykun.reduxmvi.navigation

data class NavigationRequestProps<T : NavigationRequest>(
    val navigationRequest: T,
    val consumed: () -> Unit
)
