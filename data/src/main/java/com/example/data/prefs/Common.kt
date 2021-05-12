package com.example.data.prefs

import android.content.Context
import com.example.core.prefs.DefaultPreferences
import com.example.core.prefs.LongProperty

class Common(context: Context) : DefaultPreferences(context) {

    var searchTime: Long by LongProperty(prefs) //search_time

}