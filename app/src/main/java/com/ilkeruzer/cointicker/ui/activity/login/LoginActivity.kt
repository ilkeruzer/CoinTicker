package com.ilkeruzer.cointicker.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.databinding.ActivityLoginBinding
import com.ilkeruzer.cointicker.ui.activity.main.MainActivity
import com.ilkeruzer.cointicker.util.dialog.ProgressDialog
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this, R.style.LoadingDialogStyle)

        binding.loginBtn.setOnClickListener {
            loginObserve()
        }

        allCoinObserve()
        insertObserve()
    }

    private fun insertObserve() {
        viewModel.insertStatus.observe(this, {
            if (it) {
                hideProgressDialog()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }


    private fun allCoinObserve() {
        viewModel.coinResponse.observe(this, {
            when (it.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_SUCCESS -> {
                    it.responseObject?.let { list ->
                        viewModel.insertLocalDb(list)
                    }
                    Log.d("MainActivity", it.responseObject.toString())
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    Log.e("MainActivity", it.errorMessage)
                }
            }
        })
    }

    private fun loginObserve() {
        viewModel.status.observe(this, {
            if (it) {
                viewModel.getAllCoin()
            }
        })
    }

    /**
     * Show Progress Dialog
     */
    fun showProgressDialog() {
        try {
            if (!progressDialog.isShowing) {
                progressDialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Hide Progress Dialog
     */
    fun hideProgressDialog() {
        try {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}