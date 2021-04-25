package com.ilkeruzer.cointicker.ui.activity.detail

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.databinding.ActivityDetailBinding
import com.ilkeruzer.cointicker.util.extention.*
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel


@Suppress("DeferredResultUnused")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCoin("bitcoin")
        coinObserve()

    }

    private fun coinObserve() {
        viewModel.coinResponse.observe(this, {
            when (it.status) {
                STATUS_LOADING -> {
                    binding.container.setGone()
                }
                STATUS_SUCCESS -> {
                    it.responseObject?.let { response ->
                        binding.container.setVisible()
                        binding.imageView.loadImageUrl(response.image.large)
                        binding.titleTextView.text = response.name
                        binding.priceTextView.text =
                            response.marketData.currentPrice.usd.toCurrency()
                        binding.lowestTextView.text =
                            response.marketData.lowestPrice24h.usd.toCurrency()
                        binding.highestTextView.text =
                            response.marketData.highestPrice24h.usd.toCurrency()
                        binding.hashTextView.text = response.hashingAlgorithm
                        binding.descriptionTextView.text = response.description.en.htmlToString()

                    }
                    Log.d("Detail Response", it.responseObject.toString())
                }
                STATUS_ERROR -> {
                    binding.container.setGone()
                    Log.e("Detail Response", it.errorMessage)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getCoin("bitcoin").cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_coin_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.repeat_menu -> {
                val alertDialog = AlertDialog.Builder(this).create()
                val input: EditText = EditText(this)
                input.hint = "Write Second"
                input.inputType = InputType.TYPE_CLASS_NUMBER
                input.setPadding(64,16,64,16)
                alertDialog.setTitle(getString(R.string.repeat_time))
                alertDialog.setMessage("Change Repeat time")
                alertDialog.setView(input)
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, which ->
                    dialog.dismiss()
                    viewModel.setRepeatTime(input.text.toString())
                 }
                alertDialog.show()
            }
        }

        return true

    }
}