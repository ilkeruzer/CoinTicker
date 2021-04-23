package com.ilkeruzer.cointicker

import android.app.Application
import com.ilkeruzer.cointicker.di.appModule
import com.ilkeruzer.cointicker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}