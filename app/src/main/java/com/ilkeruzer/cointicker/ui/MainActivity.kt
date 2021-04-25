package com.ilkeruzer.cointicker.ui


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilkeruzer.cointicker.R
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
        adapterLoadState()
    }

    private fun initPagerAdapter() {
        binding.recList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = coinAdapter
        }
    }

    private fun allLocalDbCoinObserve(search: String = "") {
        lifecycleScope.launchWhenCreated  {
            viewModel.flow(search).collectLatest { pagingData ->
                coinAdapter.submitData(pagingData)
            }
        }
    }

    private fun adapterLoadState() {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_coin_list, menu)
        val searchItem = menu?.findItem(R.id.action_search).apply {
//            expandActionView()
        }

        val searchView = searchItem?.actionView as SearchView

        searchView.apply {
            queryHint = getString(R.string.coin_search)
            setOnQueryTextListener(onQueryTextListener)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

        }

        return true

    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText.isNullOrEmpty().not()) {
                allLocalDbCoinObserve(newText.toString())
                Log.d("search",newText.toString())
            }
            else {
                allLocalDbCoinObserve()
            }

            return true
        }
    }
}