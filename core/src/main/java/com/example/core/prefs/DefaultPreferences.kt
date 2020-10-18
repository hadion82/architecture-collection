package com.example.core.prefs

import android.content.Context
import android.content.SharedPreferences

abstract class DefaultPreferences(
    private val context: Context
) {
    operator fun invoke(): SharedPreferences =
        context.getSharedPreferences(javaClass.simpleName, Context.MODE_PRIVATE)

    fun stringProp(defValue: String? = null, apply: Boolean = false) =
        StringProperty(this(), defValue, apply)

    fun intProp(defValue: Int = 0, apply: Boolean = false) =
        IntProperty(this(), defValue, apply)

    fun longProp(defValue: Long = 0L, apply: Boolean = false) =
        LongProperty(this(), defValue, apply)

    fun floatProp(defValue: Float = 0f, apply: Boolean = false) =
        FloatProperty(this(), defValue, apply)

    fun doubleProp(defValue: Double = 0.0, apply: Boolean = false) =
        DoubleProperty(this(), defValue, apply)

    fun booleanProp(defValue: Boolean = false, apply: Boolean = false) =
        BooleanProperty(this(), defValue, apply)
}