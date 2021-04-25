package com.ilkeruzer.cointicker.ui.activity.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.databinding.ActivityDetailBinding
import com.ilkeruzer.cointicker.util.extention.loadImageUrl
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
                        binding.titleTextView.text = response.name
                        binding.priceTextView.text = response.marketData.currentPrice.usd.toString()
                        binding.lowestTextView.text =
                            response.marketData.lowestPrice24h.usd.toString()
                        binding.highestTextView.text =
                            response.marketData.highestPrice24h.usd.toString()
                        binding.hashTextView.text = response.hashingAlgorithm
                        binding.descriptionTextView.text =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(response.description.en, Html.FROM_HTML_MODE_COMPACT)
                            } else {
                                Html.fromHtml(response.description.en)
                            }
                        binding.imageView.loadImageUrl(response.image.large)
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