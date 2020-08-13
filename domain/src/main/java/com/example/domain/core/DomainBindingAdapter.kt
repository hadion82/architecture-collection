package com.example.domain.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DomainBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadUrl(
        view: ImageView,
        url: String?
    ) = url?.let {
        Glide.with(view).load(it).into(view)
    }
}