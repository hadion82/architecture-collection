package com.example.architecture.collection.di

import com.example.data.repository.UserDefaultRepository
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.data.repository.memory.UserMemoryRepository
import com.example.data.repository.memory.UserMemoryRepositoryImpl
import com.example.data.repository.observe.UserObserveRepository
import com.example.data.repository.observe.UserObserveRepositoryImpl
import com.example.data.repository.db.UserLocalRepository
import com.example.data.repository.db.UserLocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @UserDefaultRepository
    @Singleton
    @Binds
    abstract fun provideUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @UserLocalRepository
    @Singleton
    @Binds
    abstract fun provideLocalRepository(
        localRepository: UserLocalRepositoryImpl
    ): UserRepository

    @UserMemoryRepository
    @Singleton
    @Binds
    abstract fun provideMemoryRepository(
        memoryRepository: UserMemoryRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun provideObserveRepository(
        observeRepository: UserObserveRepositoryImpl
    ): UserObserveRepository
}