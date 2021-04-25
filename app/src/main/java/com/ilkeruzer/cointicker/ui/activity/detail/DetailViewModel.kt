package com.ilkeruzer.cointicker.ui.activity.detail

import androidx.lifecycle.*
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

    private val repeatTime = MutableLiveData<Long>(5000)





    fun getCoin(id: String) = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(repeatTime.value!!) {
            _coinResponse.request(
                viewModelScope = viewModelScope,
                suspendfun = {
                    networkModule.service().getCoinById(id)
                }
            )
    }

    fun setRepeatTime(text: String) {
        getCoin("bitcoin").cancel()
        repeatTime.value = text.toLong() * 1000
        getCoin("bitcoin").start()
    }


}