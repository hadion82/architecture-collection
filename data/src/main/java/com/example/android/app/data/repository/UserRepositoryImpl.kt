package com.example.android.app.data.repository

import com.example.android.app.data.datasource.local.UserLocalDataSource
import com.example.android.app.data.datasource.remote.UserRemoteDataSource

internal class UserRepositoryImpl(
        private val localDataSource: UserLocalDataSource,
        private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun observeUser() = localDataSource.observeUser()

    override suspend fun getUser() = localDataSource.getUser()

    override suspend fun getUser(id: Long)= localDataSource.getUser(id)

    override suspend fun getUser(keyword: String) = remoteDataSource.getUser(keyword)
}