package com.ilkeruzer.cointicker.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.data.remote.model.CoinDetailResponse
import com.ilkeruzer.cointicker.util.SingleLiveEvent
import com.ilkeruzer.cointicker.util.extention.launchPeriodicAsync
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val networkModule: NetworkModule
): ViewModel() {

    private val _coinResponse: SingleLiveEvent<RESPONSE<CoinDetailResponse>> = SingleLiveEvent()
    val coinResponse: LiveData<RESPONSE<CoinDetailResponse>> = _coinResponse



    fun getCoin(id: String) = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(10000) {
            _coinResponse.request(
                viewModelScope = viewModelScope,
                suspendfun = {
                    networkModule.service().getCoinById(id)
                }
            )
    }


}