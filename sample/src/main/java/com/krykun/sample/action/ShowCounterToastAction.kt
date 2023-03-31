package com.krykun.sample.action

import com.krykun.reduxmvi.navigation.action.RequestNavigation
import com.krykun.sample.navigation.MainNavigation

class ShowCounterToastAction(val counter: Int) :
    RequestNavigation(MainNavigation.ShowCounterToast(counter))