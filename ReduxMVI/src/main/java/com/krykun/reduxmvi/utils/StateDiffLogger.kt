package com.krykun.reduxmvi.utils

import com.krykun.reduxmvi.global.AppState

interface StateDiffLogger {
    fun printDiff(oldState: AppState, newState: AppState)
}
