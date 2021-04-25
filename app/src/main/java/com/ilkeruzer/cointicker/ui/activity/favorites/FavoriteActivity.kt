package com.ilkeruzer.cointicker.ui.activity.favorites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.ilkeruzer.cointicker.data.local.CoinDbModel
import com.ilkeruzer.cointicker.databinding.ActivityFavoriteBinding
import com.ilkeruzer.cointicker.ui.activity.detail.DetailActivity
import com.ilkeruzer.cointicker.ui.adapter.FavoriteAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.FavoritesListener {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by inject<FavoriteAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favoriteObserve()
        initAdapter()

    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllFavorite()
    }

    private fun initAdapter() {
        binding.recList.adapter = adapter
        adapter.setListener(this)
    }

    private fun favoriteObserve() {
        viewModel.coinState.observe(this, {
            if (it) {
                Log.d("list", viewModel.getAllCoin().toString())
                adapter.setList(viewModel.getAllCoin())
                adapter.notifyDataSetChanged()
            }
        })
    }



    override fun favoriteClicked(data: CoinDbModel) {
        startActivity(Intent(this, DetailActivity::class.java).putExtra("data",data))
    }
}