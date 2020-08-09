package com.example.core.shared

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty

abstract class BaseProperty<T>(
    private val pref: Lazy<SharedPreferences>,
    private val name: String,
    private val defValue: T? = null
) : ReadWriteProperty<Any, T> {

    abstract fun <T> postValue(value: T)
}

//class StringPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: String? = null
//) : BaseProperty<String?>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
//        return pref.value.getString(name, defValue)
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
//        pref.value.edit {
//            putString(name, value)
//        }
//    }
//
//    override fun <T> postValue(value: T) {
//        TODO("Not yet implemented")
//    }
//}
//
//class EncryptStringPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: String? = null
//) : BaseProperty<String?>(pref, name, defValue) {
//    private val encryptor by lazy {
//        AESEncryptor()
//    }
//
//    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
//        val result = encryptor.decryptWithAES(pref.value.getString(name,null))
//        return result ?: defValue
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
//        pref.value.edit {
//            putString(name, encryptor.encrypt(value))
//        }
//    }
//}
//
//
//class IntPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: Int = 0
//) : BaseProperty<Int>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
//        return pref.value.getInt(name, defValue)
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
//        pref.value.edit {
//            putInt(name, value)
//        }
//    }
//}
//
//class LongPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: Long = 0L
//) : BaseProperty<Long>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
//        return pref.value.getLong(name, defValue)
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
//        pref.value.edit {
//            putLong(name, value)
//        }
//    }
//}
//
//class FloatPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: Float
//) : BaseProperty<Float>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): Float {
//        return pref.value.getFloat(name, defValue)
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
//        pref.value.edit {
//            putFloat(name, value)
//        }
//    }
//}
//
//class DoublePreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: Double = 0.0
//) : BaseProperty<Double>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): Double {
//
//        return Double.fromBits(
//            pref.value.getLong(name, defValue.toBits())
//        )
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) {
//        pref.value.edit {
//            putLong(name, value.toBits())
//        }
//    }
//}
//
//
//class BooleanPreference(
//    private val pref: Lazy<SharedPreferences>,
//    private val name: String,
//    private val defValue: Boolean = false
//) : BaseProperty<Boolean>(pref, name, defValue) {
//    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
//        return pref.value.getBoolean(name, defValue)
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
//        pref.value.edit {
//            putBoolean(name, value)
//        }
//    }
//}

