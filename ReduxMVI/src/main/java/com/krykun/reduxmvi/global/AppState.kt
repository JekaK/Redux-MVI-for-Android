package com.krykun.reduxmvi.global

import com.krykun.reduxmvi.navigation.NavigationRequest

/**
 * Represents the state of the whole app as composition of global state pieces as well as
 * independent feature states
 */
data class AppState(
    val navigationRequests: List<NavigationRequest> = emptyList(),
    val viewState: Any = Any()
)
