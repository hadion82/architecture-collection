package com.example.architecture.collection.di

import android.content.Context
import com.example.data.prefs.Common
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideCommonPrefs(@ApplicationContext context: Context) =
        Common(context)
}