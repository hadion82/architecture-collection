package com.example.data.prefs

import android.content.Context
import com.example.core.prefs.DefaultPreferences

class Common(context: Context) : DefaultPreferences(context) {

    var searchTime: Long by longProp()
}