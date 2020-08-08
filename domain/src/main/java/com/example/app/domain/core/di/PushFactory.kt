package com.example.app.domain.core.di

import com.example.app.domain.core.push.Push
import javax.inject.Inject
import javax.inject.Provider

class PushFactory @Inject constructor(
    private val creators:Map<Class<out Push>, @JvmSuppressWildcards Provider<Push>>
): IPushFactory {

    override fun <T : Push> create(modelClass: Class<T>): T? {
        val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
        val creator = found?.value
        try {
            @Suppress("UNCHECKED_CAST")
            return if(creator == null)
                modelClass.newInstance()
            else creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return null
    }
}