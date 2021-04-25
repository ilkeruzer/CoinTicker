package com.ilkeruzer.cointicker.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.data.remote.model.CoinDetailResponse
import com.ilkeruzer.cointicker.util.SingleLiveEvent
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request

class DetailViewModel(
    private val networkModule: NetworkModule
): ViewModel() {

    private val _coinResponse: SingleLiveEvent<RESPONSE<CoinDetailResponse>> = SingleLiveEvent()
    val coinResponse: LiveData<RESPONSE<CoinDetailResponse>> = _coinResponse

    init {
        getCoin("bitcoin")
    }

    fun getCoin(id: String) {
        _coinResponse.request(
            viewModelScope = viewModelScope,
            suspendfun = {
                networkModule.service().getCoinById(id)
            }
        )
    }
}