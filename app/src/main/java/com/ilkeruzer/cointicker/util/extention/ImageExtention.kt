package com.ilkeruzer.cointicker.util.extention

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ilkeruzer.cointicker.R

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context)
            .load(url)
            .apply(
                    RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            )
            .error(R.drawable.ic_image_not_supported)
            .into(this)

}