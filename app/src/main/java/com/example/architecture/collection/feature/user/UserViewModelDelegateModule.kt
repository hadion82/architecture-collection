package com.example.architecture.collection.feature.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserViewModelDelegateModule {

    @Singleton
    @Provides
    fun provideUserViewModelDelegateImpl(
        intentProcessor: UserIntentProcessor,
        actionProcessor: UserActionProcessor
    ): UserViewModelDelegate = UserViewModelDelegateImpl(
        intentProcessor, actionProcessor
    )
}