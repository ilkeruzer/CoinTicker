package com.ilkeruzer.cointicker.ui.activity.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ilkeruzer.cointicker.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favoriteObserve()
    }

    private fun favoriteObserve() {
        viewModel.coinState.observe(this, {
            if (it)
                Log.d("coinlist",viewModel.getAllCoin().toString())
        })
    }
}