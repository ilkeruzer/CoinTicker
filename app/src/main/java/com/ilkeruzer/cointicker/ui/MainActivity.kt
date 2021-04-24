package com.ilkeruzer.cointicker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilkeruzer.cointicker.databinding.ActivityMainBinding
import com.ilkeruzer.cointicker.ui.adapter.CoinAdapter
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val coinAdapter by inject<CoinAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPagerAdapter()
        allCoinObserve()
        allLocalDbCoinObserve()
    }

    private fun initPagerAdapter() {
        binding.recList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = coinAdapter
        }
    }

    private fun allLocalDbCoinObserve() {
        lifecycleScope.launchWhenCreated  {
            viewModel.flow.collectLatest { pagingData ->
                coinAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            coinAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }.collect {
                    binding.recList.scrollToPosition(0)
                }
        }
    }

    private fun allCoinObserve() {
        viewModel.coinResponse.observe(this, {
            when (it.status) {
                STATUS_LOADING -> {
                }
                STATUS_SUCCESS -> {
                    it.responseObject?.let { list ->
                        viewModel.insertLocalDb(list)
                    }
                    Log.d("MainActivity", it.responseObject.toString())
                }
                STATUS_ERROR -> {
                    Log.e("MainActivity", it.errorMessage)
                }
            }
        })
    }
}