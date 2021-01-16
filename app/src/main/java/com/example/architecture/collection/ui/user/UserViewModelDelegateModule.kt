package com.example.architecture.collection.ui.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UserViewModelDelegateModule {

    @Singleton
    @Provides
    fun provideUserViewModelDelegate(
        intentProcessor: UserIntentProcessor,
        actionProcessor: UserActionProcessor
    ): UserViewModelDelegate = UserViewModelDelegateImpl(
        intentProcessor, actionProcessor
    )
}