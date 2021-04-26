package com.ilkeruzer.cointicker.ui.activity.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ilkeruzer.cointicker.data.local.CoinDbModel

class FavoriteViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var coinList = ArrayList<CoinDbModel>()

    private val _coinState = MutableLiveData(false)
    val coinState: LiveData<Boolean> = _coinState


    private val db = Firebase.firestore
            .collection("Cryptocurrency")
            .document(firebaseAuth.currentUser.uid)
            .collection("favorite")


    fun getAllFavorite() {
        coinList.clear()
        db.get()
                .addOnSuccessListener { document ->
                    val list = document.documents
                    list.forEach {
                        val coinDbModel = it.toObject(CoinDbModel::class.java)
                        coinDbModel?.let { favorite ->
                            coinList.add(favorite)
                        }
                    }
                    _coinState.value = true
                }
                .addOnFailureListener { exception ->
                    Log.e("error", exception.toString())
                    _coinState.value = false
                }
    }

    fun getAllCoin(): List<CoinDbModel> {
        return coinList
    }
}