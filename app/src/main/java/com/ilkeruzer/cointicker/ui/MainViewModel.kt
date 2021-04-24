package com.ilkeruzer.cointicker.ui

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
    private val networkModule: NetworkModule,
    private val coinDatabase: CoinDatabase
) : ViewModel() {

    private val _coinResponse: SingleLiveEvent<RESPONSE<List<CoinResponse>>> = SingleLiveEvent()
    val coinResponse: LiveData<RESPONSE<List<CoinResponse>>> = _coinResponse

    init {
        getAllCoin()
    }

    private fun getAllCoin() {
        _coinResponse.request(
            viewModelScope = viewModelScope,
            suspendfun = {
                networkModule.service().coinList()
            })
    }

    fun insertLocalDb(list: List<CoinResponse>) = CoroutineScope(Dispatchers.IO).launch {
        val dbList = ArrayList<CoinDbModel>()
        list.forEach {
            dbList.add(
                CoinDbModel(
                    code = it.id,
                    symbol = it.symbol,
                    name = it.name
            ))
        }

        coinDatabase.coinDao().insertAll(dbList)
    }

    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        coinDatabase.coinDao().getAllCoins()
    }.flow
        .cachedIn(viewModelScope)
}