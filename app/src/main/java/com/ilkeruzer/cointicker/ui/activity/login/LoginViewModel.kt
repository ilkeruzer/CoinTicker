package com.ilkeruzer.cointicker.ui.activity.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
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

class LoginViewModel(
        private val networkModule: NetworkModule,
        private val coinDatabase: CoinDatabase
) : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _status = MutableLiveData(false)
    val status: LiveData<Boolean> = _status

    private val _insertStatus = MutableLiveData(false)
    val insertStatus: LiveData<Boolean> = _insertStatus

    private val _coinResponse: SingleLiveEvent<RESPONSE<List<CoinResponse>>> = SingleLiveEvent()
    val coinResponse: LiveData<RESPONSE<List<CoinResponse>>> = _coinResponse


    init {
        clearAllCoin()
        login("test@cointicker.com", "123456")
    }

    private fun clearAllCoin() = CoroutineScope(Dispatchers.IO).launch {
        coinDatabase.coinDao().clearStation()
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _status.postValue(true)
                }.addOnFailureListener { exception ->
                    _status.postValue(false)
                    Log.v("errorLogin", exception.toString())
                }
    }

    fun getAllCoin() {
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
                    )
            )
        }

        try {
            coinDatabase.coinDao().insertAll(dbList)
            _insertStatus.postValue(true)
        } catch (e: Exception) {
            _insertStatus.postValue(false)
        }

    }
}