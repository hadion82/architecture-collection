package com.example.core.prefs

import android.content.Context
import android.content.SharedPreferences

abstract class DefaultPreferences(
    context: Context
) {
    val prefs: SharedPreferences = context.getSharedPreferences(
        javaClass.simpleName, Context.MODE_PRIVATE
    )
}