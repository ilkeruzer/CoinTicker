package com.ilkeruzer.cointicker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.data.remote.model.CoinResponse
import com.ilkeruzer.cointicker.util.SingleLiveEvent
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request

class MainViewModel(
    private val networkModule: NetworkModule
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
}