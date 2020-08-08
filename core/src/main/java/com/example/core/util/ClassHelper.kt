package com.example.core.util

import com.example.android.retrofit.annotation.ReqBody
import com.example.android.retrofit.annotation.ReqQuery
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


    fun <T : Any> toRequestBody(t: T): HashMap<String, Any>? {
        val result = HashMap<String, Any>()
        val fieldList = getFields(t)

        for (field in fieldList) {
            try {
                field.isAccessible = true
                field.getAnnotation(ReqBody::class.java).let {
                    result[it.value] = field.get(t)
                }
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


    fun <T : Any> toRequestQuery(t: T): HashMap<String, String> {
        val result = HashMap<String, String>()
        val fieldList = getFields(t)
        for (field in fieldList) {
            try {
                field.isAccessible = true
                field.getAnnotation(ReqQuery::class.java).let {
                    result.put(it.value, field.get(t).toString())
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        return result
    }

//    fun <T> getReqBodyMap(t: T): ReqBodyMap<String, RequestBody>? {
//        val result = ReqBodyMap.PermissionBuilder()
//        val fieldList = getFields(t)
//
//        for (field in fieldList) {
//            try {
//                field.isAccessible = true
//                val reqBody = field.getAnnotation(ReqBody::class.java!!)
//                if (reqBody != null) {
//                    result.put(reqBody!!.value(), createRequestBody(field.get(t), true))
//                    continue
//                }
//                val reqFile = field.getAnnotation(ReqFile::class.java!!)
//                if (reqFile != null) {
//                    result.put(reqFile!!.value(), createRequestFile(reqFile!!.type(), field.get(t)))
//                }
//            } catch (e: IllegalArgumentException) {
//                e.printStackTrace()
//                return null
//            } catch (e: IllegalAccessException) {
//                e.printStackTrace()
//                return null
//            }
//
//        }
//        return result.build()
//    }
//
//    fun <T> getReqBody(t: T): RequestBody {
//        return createRequestBody(toRequestBody(t), false)
//    }

    public fun <T : Any> getFields(t: T): List<Field> {
        val fieldList = ArrayList<Field>()
        var tempClass: Class<*>? = t::class.java
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(*tempClass.declaredFields))
            tempClass = tempClass.superclass
        }
        return fieldList
    }


//    fun createRequestBody(`object`: Any?, multipart: Boolean): RequestBody {
//        return if (multipart)
//            createRequestBody(`object`, "multipart/form-data", false)
//        else
//            createRequestBody(`object`, "application/json", true)
//    }
//
//    fun createRequestBody(any: Any?, type: String, json: Boolean): RequestBody {
//        return RequestBody.create(MediaType.parse(type), if (json) GsonUtils.toJson(any) else any.toString())
//    }
//
//    fun createRequestFile(type: String, path: Any): RequestBody {
//        return RequestBody.create(MediaType.parse(type), File(path.toString()))
//    }
}