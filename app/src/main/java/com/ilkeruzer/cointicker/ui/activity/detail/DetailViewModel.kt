package com.ilkeruzer.cointicker.ui.activity.detail

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.ilkeruzer.cointicker.data.local.CoinDbModel
import com.ilkeruzer.cointicker.data.remote.NetworkModule
import com.ilkeruzer.cointicker.data.remote.model.CoinDetailResponse
import com.ilkeruzer.cointicker.util.SingleLiveEvent
import com.ilkeruzer.cointicker.util.extention.launchPeriodicAsync
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailViewModel(
        private val networkModule: NetworkModule
) : ViewModel() {

    private val _coinResponse: SingleLiveEvent<RESPONSE<CoinDetailResponse>> = SingleLiveEvent()
    val coinResponse: LiveData<RESPONSE<CoinDetailResponse>> = _coinResponse

    private val repeatTime = MutableLiveData<Long>(60000)

    private lateinit var coinDbModel: CoinDbModel
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _addedFavorite = MutableLiveData(false)
    val addedFavorite: LiveData<Boolean> = _addedFavorite

    private val _isFavourite = MutableLiveData(false)
    val isFavourite: LiveData<Boolean> = _isFavourite

    private val _isFavouriteDeleted = MutableLiveData(false)
    val isFavouriteDeleted: LiveData<Boolean> = _isFavouriteDeleted

    var favourite: Boolean = false


    private val db = Firebase.firestore
            .collection("Cryptocurrency")
            .document(firebaseAuth.currentUser.uid)
            .collection("favorite")


    fun getCoin() = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(repeatTime.value!!) {
        _coinResponse.request(
                viewModelScope = viewModelScope,
                suspendfun = {
                    networkModule.service().getCoinById(coinDbModel.code)
                }
        )
    }

    fun addToFavourites() {

        val favouriteDocument = db.document(coinDbModel.name)

        favouriteDocument.set(coinDbModel)
                .addOnSuccessListener {
                    _addedFavorite.postValue(true)
                }
                .addOnFailureListener {
                    _addedFavorite.postValue(false)
                }

    }

    fun isFavourite() {
        val favouriteDocument = db.document(coinDbModel.name)

        favouriteDocument.get()
                .addOnSuccessListener { document ->
                    _isFavourite.value = document.exists()
                }
                .addOnFailureListener { exception ->
                    _isFavourite.value = false
                }
    }

    fun deleteFavourite() {
        val favouriteDocument = db.document(coinDbModel.name)

        favouriteDocument
                .delete()
                .addOnSuccessListener { _isFavouriteDeleted.postValue(true) }
                .addOnFailureListener { _isFavouriteDeleted.postValue(false) }
    }

    fun setRepeatTime(text: String) {
        getCoin().cancel()
        repeatTime.value = text.toLong() * 1000
        getCoin().start()
    }

    fun setCoinExtras(coinDbModel: CoinDbModel) {
        this.coinDbModel = coinDbModel
    }

    fun getCoinModel(): CoinDbModel {
        return coinDbModel
    }


}