package com.example.architecture.collection.di

import com.example.data.repository.DefaultUserRepository
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.data.repository.memory.MemoryRepository
import com.example.data.repository.memory.MemoryRepositoryImpl
import com.example.data.repository.observe.ObserveRepository
import com.example.data.repository.observe.ObserveRepositoryImpl
import com.example.data.repository.db.LocalRequestRepository
import com.example.data.repository.db.LocalRequestRepositoryImpl
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

    @LocalRequestRepository
    @Singleton
    @Binds
    abstract fun provideSingleRequestRepository(
        singleRequestRepository: LocalRequestRepositoryImpl
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