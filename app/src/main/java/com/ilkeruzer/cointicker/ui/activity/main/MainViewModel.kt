package com.ilkeruzer.cointicker.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilkeruzer.cointicker.data.local.CoinDatabase
import com.ilkeruzer.cointicker.data.local.CoinDbModel
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.data.remote.model.CoinResponse
import com.ilkeruzer.cointicker.util.SingleLiveEvent
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val coinDatabase: CoinDatabase
) : ViewModel() {


    fun flow(search: String = "") = Pager(
        PagingConfig(pageSize = 20)
    ) {
        if (search == "") {
            coinDatabase.coinDao().getAllCoins()
        } else {
            coinDatabase.coinDao().getCoinsBySearch(search)
        }
    }.flow
        .cachedIn(viewModelScope)
}