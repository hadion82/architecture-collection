package com.example.android.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import com.example.android.app.data.datasource.local.UserLocalDataSource
import com.example.android.app.data.datasource.remote.UserRemoteDataSource
import com.example.android.app.data.repository.UserRepository
import com.example.android.app.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
@Singleton
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    internal fun provideUserRepository(
        local: UserLocalDataSource,
        remote: UserRemoteDataSource
    ): UserRepository =
        UserRepositoryImpl(local, remote)
}