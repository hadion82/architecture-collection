package com.example.data.repository

import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
        private val localDataSource: UserLocalDataSourceImpl,
        private val remoteDataSource: UserRemoteDataSourceImpl
) : UserRepository {

    override fun observeUser() = localDataSource.observeUser()

    override suspend fun getUser() = localDataSource.getUser()

    override suspend fun getUser(id: Long)= localDataSource.getUser(id)

    override suspend fun getCount() = localDataSource.getCount()

    override suspend fun getUser(keyword: String) = remoteDataSource.getUser(keyword)

    override fun loadUsers() = localDataSource.loadUsers()
}