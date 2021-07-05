package com.example.core.util

import java.lang.reflect.Field
import java.util.*
import kotlin.collections.HashMap

object ClassHelper {

    fun <T : Any> toMap(t: T): Map<String, Any>? {
        val result = HashMap<String, Any>()
        val fieldList = getFields(t)

        for (field in fieldList) {
            try {
                field.isAccessible = true
                result[field.name] = field.get(t)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                return null
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                return null
            }

        }
        return result
    }

    private fun <T : Any> getFields(t: T): List<Field> {
        val fieldList = ArrayList<Field>()
        var tempClass: Class<*>? = t::class.java
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(*tempClass.declaredFields))
            tempClass = tempClass.superclass
        }
        return fieldList
    }
}