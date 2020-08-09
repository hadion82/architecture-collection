package com.example.data.repository

import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource

class UserRepositoryImpl(
        private val localDataSource: UserLocalDataSource,
        private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun observeUser() = localDataSource.observeUser()

    override suspend fun getUser() = localDataSource.getUser()

    override suspend fun getUser(id: Long)= localDataSource.getUser(id)

    override suspend fun getCount() = localDataSource.getCount()

    override suspend fun getUser(keyword: String) = remoteDataSource.getUser(keyword)

    override fun loadUsers() = localDataSource.loadUsers()
}