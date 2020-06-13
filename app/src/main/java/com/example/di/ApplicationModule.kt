package com.example.di

import android.content.Context
import com.example.DemoApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun providesContext(application: DemoApplication): Context {
        return application
    }
}