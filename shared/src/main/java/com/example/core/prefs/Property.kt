package com.example.core.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class BaseProperty<T>(
    protected val prefs: SharedPreferences,
    protected val apply: Boolean = false
) : ReadWriteProperty<Any, T> {

    private var key: String? = null

    companion object {
        val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    }

    fun getKey(name: String): String =
        key ?: camelRegex.replace(name) {
            "_${it.value}"
        }.toLowerCase(Locale.ROOT).apply {
            key = this
        }
}


class StringProperty(
    prefs: SharedPreferences,
    private val defValue: String? = null,
    apply: Boolean = false
) : BaseProperty<String?>(prefs, apply) {
    override fun getValue(thisRef: Any, property: KProperty<*>): String? =
        prefs.getString(getKey(property.name), defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) =
        prefs.edit(apply) {
            putString(getKey(property.name), value)
        }
}

class IntProperty(
    prefs: SharedPreferences,
    private val defValue: Int = 0,
    apply: Boolean = false
) : BaseProperty<Int>(prefs, apply) {

    override fun getValue(thisRef: Any, property: KProperty<*>): Int =
        prefs.getInt(getKey(property.name), defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
        prefs.edit(apply) {
            putInt(getKey(property.name), value)
        }
}

class LongProperty(
    prefs: SharedPreferences,
    private val defValue: Long = 0L,
    apply: Boolean = false
) : BaseProperty<Long>(prefs, apply) {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long =
        prefs.getLong(getKey(property.name), defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
        prefs.edit(apply) {
            putLong(getKey(property.name), value)
        }
}

class FloatProperty(
    prefs: SharedPreferences,
    private val defValue: Float = 0.0f,
    apply: Boolean = false
) : BaseProperty<Float>(prefs, apply) {

    override fun getValue(thisRef: Any, property: KProperty<*>): Float =
        prefs.getFloat(getKey(property.name), defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) =
        prefs.edit(apply) {
            putFloat(getKey(property.name), value)
        }
}

class DoubleProperty(
    prefs: SharedPreferences,
    private val defValue: Double = 0.0,
    apply: Boolean = false
) : BaseProperty<Double>(prefs, apply) {

    override fun getValue(thisRef: Any, property: KProperty<*>): Double =
        Double.fromBits(
            prefs.getLong(getKey(property.name), defValue.toBits())
        )

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) =
        prefs.edit(apply) {
            putLong(getKey(property.name), value.toBits())
        }
}


class BooleanProperty(
    prefs: SharedPreferences,
    private val defValue: Boolean = false,
    apply: Boolean = false
) : BaseProperty<Boolean>(prefs, apply) {

    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
        prefs.getBoolean(getKey(property.name), defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        prefs.edit(apply) {
            putBoolean(getKey(property.name), value)
        }
}
