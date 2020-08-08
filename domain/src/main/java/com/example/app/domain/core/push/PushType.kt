package com.example.app.domain.core.push

import com.example.app.domain.core.di.IPushFactory
import java.lang.reflect.InvocationTargetException

object PushType {

    private val push: HashMap<String, Class<out Push>> = HashMap()

    fun init(vararg classes: Class<out Push>) {
        classes.forEach { put(it) }
    }

    fun put(clazz: Class<out Push>) {
        val type = clazz.getAnnotation(com.example.core.annotation.Type::class.java)
        type?.run { push[value] = clazz}
    }

    @Throws(IllegalAccessException::class, IllegalArgumentException::class
        , InstantiationException::class, InvocationTargetException::class)
    fun toPush(type: String, factory: IPushFactory): Push? {
        return factory.create(
            from(
                type
            )
        )
    }

    fun from(value: String): Class<out Push> {
        return push[value] ?: UnknownPush::class.java
    }
}