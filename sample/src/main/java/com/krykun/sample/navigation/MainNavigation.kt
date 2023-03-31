package com.krykun.sample.navigation

import com.krykun.reduxmvi.navigation.NavigationRequest

sealed class MainNavigation : NavigationRequest {
    object EmptyNavEvent : MainNavigation()
    data class ShowCounterToast(val counter: Int) : MainNavigation()
}