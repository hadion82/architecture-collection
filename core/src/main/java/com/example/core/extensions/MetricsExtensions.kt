package com.example.core.extensions

import android.content.res.Resources

val Int.dpi: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.pixel: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dpi: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.pixel: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()