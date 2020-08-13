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

    @JvmStatic
    @BindingAdapter("longTime", "dateFormat")
    fun showFormatTime(view: TextView, longTime: Long?, dateFormat: String) {
        val dateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        view.text = dateFormat.format(Date(longTime ?: 0L))
    }

    @JvmStatic
    @BindingAdapter("longDate", "dateFormat")
    fun showFormatDate(view: TextView, longTime: Long, dateFormat: String) {
        val dateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        val date = if (longTime != 0L) Date(longTime) else Date()
        view.text = dateFormat.format(date)
    }
}