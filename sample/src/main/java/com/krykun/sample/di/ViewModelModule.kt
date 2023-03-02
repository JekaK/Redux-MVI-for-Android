package com.krykun.sample.di

import com.krykun.reduxmvi.di.BINDING_DISPATCHER
import com.krykun.sample.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = arrayOf(module {
    viewModel { MainViewModel(store = get(), bindingDispatcher = get(named(BINDING_DISPATCHER))) }
})