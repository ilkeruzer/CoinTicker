package com.ilkeruzer.cointicker.di

import com.ilkeruzer.cointicker.data.local.CoinDatabase
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.ui.adapter.FavoriteAdapter
import com.ilkeruzer.cointicker.ui.activity.detail.DetailViewModel
import com.ilkeruzer.cointicker.ui.activity.favorites.FavoriteViewModel
import com.ilkeruzer.cointicker.ui.activity.login.LoginViewModel
import com.ilkeruzer.cointicker.ui.activity.main.MainViewModel
import com.ilkeruzer.cointicker.ui.adapter.CoinAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NetworkModule() }
    single { CoinDatabase.create(androidContext()) }

    factory { CoinAdapter() }
    factory { FavoriteAdapter(arrayListOf()) }

}

val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { FavoriteViewModel() }
}