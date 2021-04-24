package com.ilkeruzer.cointicker.di

import com.ilkeruzer.cointicker.data.local.CoinDatabase
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.ui.MainViewModel
import com.ilkeruzer.cointicker.ui.adapter.CoinAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NetworkModule() }
    single { CoinDatabase.create(androidContext()) }

    factory { CoinAdapter() }

}

val viewModelModule = module {

    viewModel { MainViewModel(get(),get()) }
}