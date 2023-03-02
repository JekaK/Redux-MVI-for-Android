package com.krykun.reduxmvi.global

import com.krykun.reduxmvi.navigation.NavigationRequest
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents the state of the whole app as composition of global state pieces as well as
 * independent feature states
 */
data class AppState(
    val navigationRequests: List<NavigationRequest> = emptyList(),
    val stateSet: HashSet<MutableStateFlow<Any>> = hashSetOf()
)
