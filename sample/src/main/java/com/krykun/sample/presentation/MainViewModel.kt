package com.krykun.sample.presentation

import androidx.lifecycle.ViewModel
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import kotlinx.coroutines.CoroutineDispatcher

class MainViewModel(
    private val store: Store<Action, AppState>,
    private val bindingDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
//        store.dispatch(AddStateAction())
    }
}