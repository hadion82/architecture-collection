package com.example.core.ui.listener

import android.view.View

interface Throttle {

    interface ClickListener {
        fun throttleClick(view: View, skipDuration: Long = 500L, onClick: () -> Unit)
    }
}