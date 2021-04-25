package com.ilkeruzer.cointicker.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.ilkeruzer.cointicker.R

/**
 * Created by İlker Üzer on 12/24/2020.
 * Copyright © 2020 İlker Üzer. All rights reserved.
 */
class ProgressDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_progress)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        findViewById<ProgressBar>(R.id.mCustomProgress).indeterminateDrawable?.setColorFilter(
                ContextCompat.getColor(context, android.R.color.white),
                PorterDuff.Mode.SRC_IN
        )
    }
}