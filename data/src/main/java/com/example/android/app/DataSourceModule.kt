package com.example.android.app

import com.example.android.app.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import com.example.android.app.data.dao.UserDao
import com.example.android.app.data.datasource.local.UserLocalDataSource
import com.example.android.app.data.datasource.local.UserLocalDataSourceImpl
import com.example.android.app.data.datasource.remote.UserRemoteDataSource
import com.example.android.app.data.datasource.remote.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@Singleton
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Provides
    internal fun provideUserLocalDataSource(
        dao: UserDao
    ): UserLocalDataSource =
        UserLocalDataSourceImpl(dao)

    @Provides
    internal fun provideUserRemoteDataSource(
        service: GithubApi
    ): UserRemoteDataSource =
        UserRemoteDataSourceImpl(service)
}