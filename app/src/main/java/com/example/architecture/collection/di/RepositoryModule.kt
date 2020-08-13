package com.example.architecture.collection.di

import android.content.Context
import com.example.data.RepositoryLocator
import com.example.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository =
        RepositoryLocator.createUserRepository(context)
}