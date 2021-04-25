package com.ilkeruzer.cointicker.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.ui.activity.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.status.observe(this, {
            if (it) {
                startActivity(Intent(this,MainActivity::class.java))
            }
        })

    }
}