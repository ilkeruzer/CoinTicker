package com.ilkeruzer.cointicker.ui.activity.detail

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.data.local.CoinDbModel
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
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        viewModel.isFavourite()
        viewModel.getCoin()
        coinObserve()
        addedFavoriteObserve()
        isFavoriteObserve()
        deleteObserve()
    }

    private fun deleteObserve() {
        viewModel.isFavouriteDeleted.observe(this, {
            if (it) {
                menu?.findItem(R.id.favorite_menu)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            }
        })
    }

    private fun isFavoriteObserve() {
        viewModel.isFavourite.observe(this, {
            if (it) {
                viewModel.favourite = true
                menu?.findItem(R.id.favorite_menu)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            } else {
                viewModel.favourite = false
                menu?.findItem(R.id.favorite_menu)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            }
        })
    }

    private fun addedFavoriteObserve() {
        viewModel.addedFavorite.observe(this, {
            Log.d("added", it.toString())
            if (it) {
                menu?.findItem(R.id.favorite_menu)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            }
        })
    }

    private fun getData() {
        intent?.extras?.get("data")?.let {
            viewModel.setCoinExtras(it as CoinDbModel)
        }
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
        viewModel.getCoin().cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_coin_detail, menu)
        if (menu != null) {
            this.menu = menu
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.repeat_menu -> {
                val alertDialog = AlertDialog.Builder(this).create()
                val input: EditText = EditText(this)
                input.hint = "Write Second"
                input.inputType = InputType.TYPE_CLASS_NUMBER
                input.setPadding(64, 16, 64, 16)
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

            R.id.favorite_menu -> {
                if (viewModel.favourite) {
                    viewModel.deleteFavourite()
                } else {
                    viewModel.addToFavourites()
                }
                viewModel.favourite = !viewModel.favourite
            }
        }

        return true

    }
}