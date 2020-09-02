package com.example.architecture.collection.initializer

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
internal interface DataInitializerEntryPoint {

    companion object {
        internal fun get(context: Context): DataInitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                DataInitializerEntryPoint::class.java
            )
        }
    }

    fun inject(initializer: UserRepositoryInitializer)
}
