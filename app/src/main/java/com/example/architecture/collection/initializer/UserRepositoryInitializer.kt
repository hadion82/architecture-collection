package com.example.architecture.collection.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.data.repository.UserRepositoryImpl
import javax.inject.Inject

class UserRepositoryInitializer: Initializer<UserRepositoryImpl> {

    @Inject lateinit var repository: UserRepositoryImpl

    override fun create(context: Context): UserRepositoryImpl {
        DataInitializerEntryPoint.get(context).inject(this)
        return repository
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(DatabaseModuleInitializer::class.java, ApiModuleInitializer::class.java)
}