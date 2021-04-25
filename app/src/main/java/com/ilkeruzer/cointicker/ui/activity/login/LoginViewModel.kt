package com.ilkeruzer.cointicker.ui.activity.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _status = MutableLiveData(false)
    val status: LiveData<Boolean> = _status


    init {
        login("test@cointicker.com","123456")
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
}