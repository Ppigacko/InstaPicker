package com.ppigacko.instapostimagepicker.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

@BindingAdapter(value = ["changeHeight"], requireAll = false)
fun View.setHeight(height: Int?) {
    height?.let {
        layoutParams = layoutParams.apply {
            this.height = it
        }
    }
}