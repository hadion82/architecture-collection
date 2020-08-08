package com.example.core.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class GsonDateAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {

    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        src?.let { return JsonPrimitive(it.time as String) }
        return JsonPrimitive("")
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
        json?.let { return Date(it.asLong) }
        return Date()
    }
}