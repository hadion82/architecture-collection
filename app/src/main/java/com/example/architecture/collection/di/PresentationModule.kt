package com.example.architecture.collection.di

import com.example.architecture.collection.ui.user.UserIntentProcessor
import com.example.core.scope.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object PresentationModule {

    @FeatureScope
    @Provides
    fun provideUserIntentProcessor() =
        UserIntentProcessor()
}