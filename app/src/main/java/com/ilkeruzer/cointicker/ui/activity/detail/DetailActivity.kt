package com.ilkeruzer.cointicker.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}