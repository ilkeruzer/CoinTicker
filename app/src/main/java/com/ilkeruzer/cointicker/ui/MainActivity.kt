package com.ilkeruzer.cointicker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ilkeruzer.cointicker.databinding.ActivityMainBinding
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allCoinObserve()
    }

    private fun allCoinObserve() {
        viewModel.coinResponse.observe(this, {
            when (it.status) {
                STATUS_LOADING -> {}
                STATUS_SUCCESS -> {
                    Log.d("MainActivity",it.responseObject.toString())
                }
                STATUS_ERROR -> {
                    Log.e("MainActivity",it.errorMessage)
                }
            }
        })
    }
}