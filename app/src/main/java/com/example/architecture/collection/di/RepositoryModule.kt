package com.example.architecture.collection.di

import com.example.data.repository.*
import com.example.data.repository.memory.MemoryRepository
import com.example.data.repository.memory.MemoryRepositoryImpl
import com.example.data.repository.observe.ObserveRepository
import com.example.data.repository.observe.ObserveRepositoryImpl
import com.example.data.repository.single.SingleRequestRepository
import com.example.data.repository.single.SingleRequestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @DefaultUserRepository
    @Singleton
    @Binds
    abstract fun provideUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @SingleRequestRepository
    @Singleton
    @Binds
    abstract fun provideSingleRequestRepository(
        singleRequestRepository: SingleRequestRepositoryImpl
    ): UserRepository

    @MemoryRepository
    @Singleton
    @Binds
    abstract fun provideMemoryRepository(
        memoryRepository: MemoryRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun provideObserveRepository(
        observeRepository: ObserveRepositoryImpl
    ): ObserveRepository
}