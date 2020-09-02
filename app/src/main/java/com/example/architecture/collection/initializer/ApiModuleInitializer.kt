package com.example.architecture.collection.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.architecture.collection.di.ApiModule

class ApiModuleInitializer: Initializer<ApiModule> {

    override fun create(context: Context): ApiModule =
        ApiModule

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()
}