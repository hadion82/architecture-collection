package com.example.architecture.collection.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.architecture.collection.di.DatabaseModule

class DatabaseModuleInitializer: Initializer<DatabaseModule> {

    override fun create(context: Context): DatabaseModule =
        DatabaseModule

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()
}