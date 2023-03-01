package com.krykun.reduxmvi.navigation

import com.krykun.reduxmvi.ext.takeWhenChanged
import com.krykun.reduxmvi.global.Action
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.global.Store
import com.krykun.reduxmvi.navigation.action.ConsumeNavigation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

inline fun <reified T : NavigationRequest> Store<Action, AppState>.navigationFlowOf(dispatcher: CoroutineDispatcher) =
    this.stateFlow()
        .takeWhenChanged { it.navigationRequests }
        .map { it.filterIsInstance<T>() }
        .filterNot { it.isEmpty() }
        .map {
            val firstRequest = it.first()
            NavigationRequestProps(firstRequest) {
                this.dispatch(
                    ConsumeNavigation(firstRequest)
                )
            }
        }
        .flowOn(dispatcher)
