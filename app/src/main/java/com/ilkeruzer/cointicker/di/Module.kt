package com.ilkeruzer.cointicker.di

import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NetworkModule() }

}

val viewModelModule = module {

    viewModel { MainViewModel(get()) }
}