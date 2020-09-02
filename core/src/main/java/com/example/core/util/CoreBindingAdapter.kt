package com.example.core.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.core.ui.listener.Throttle
import java.text.SimpleDateFormat
import java.util.*

object CoreBindingAdapter {

    @JvmStatic
    @BindingAdapter("owner", "click", "duration")
    fun throttleClick(
        view: View,
        model: Throttle.ClickListener,
        onClick: () -> Unit,
        skipDuration: Long = 500
    ) {
        model.throttleClick(view, skipDuration, onClick)
    }
}