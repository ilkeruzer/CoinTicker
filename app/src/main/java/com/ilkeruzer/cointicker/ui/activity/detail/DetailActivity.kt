package com.ilkeruzer.cointicker.ui.activity.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ilkeruzer.cointicker.databinding.ActivityDetailBinding
import com.ilkeruzer.cointicker.util.extention.htmlToString
import com.ilkeruzer.cointicker.util.extention.loadImageUrl
import com.ilkeruzer.cointicker.util.extention.toCurrency
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coinObserve()

    }

    private fun coinObserve() {
        viewModel.coinResponse.observe(this, {
            when (it.status) {
                STATUS_LOADING -> {
                }
                STATUS_SUCCESS -> {
                    it.responseObject?.let { response ->
                        binding.imageView.loadImageUrl(response.image.large)
                        binding.titleTextView.text = response.name
                        binding.priceTextView.text = response.marketData.currentPrice.usd.toCurrency()
                        binding.lowestTextView.text = response.marketData.lowestPrice24h.usd.toCurrency()
                        binding.highestTextView.text = response.marketData.highestPrice24h.usd.toCurrency()
                        binding.hashTextView.text = response.hashingAlgorithm
                        binding.descriptionTextView.text = response.description.en.htmlToString()

                    }
                    Log.d("Detail Response", it.responseObject.toString())
                }
                STATUS_ERROR -> {
                    Log.e("Detail Response", it.errorMessage)
                }
            }
        })
    }
}