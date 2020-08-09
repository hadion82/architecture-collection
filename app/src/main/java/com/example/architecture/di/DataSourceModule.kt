package com.example.architecture.di

import com.example.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import com.example.data.dao.UserDao
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideUserLocalDataSource(
        dao: UserDao
    ): UserLocalDataSource =
        UserLocalDataSourceImpl(dao)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        service: GithubApi
    ): UserRemoteDataSource =
        UserRemoteDataSourceImpl(service)
}