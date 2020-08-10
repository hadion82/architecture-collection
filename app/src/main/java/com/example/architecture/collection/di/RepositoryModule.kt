package com.example.architecture.collection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        local: UserLocalDataSource,
        remote: UserRemoteDataSource
    ): UserRepository =
        UserRepositoryImpl(local, remote)
}