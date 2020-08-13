package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.entity.UserEntity

internal class UserRepositoryImpl (
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun observeUser() = localDataSource.observeUser()

    override suspend fun getUser() = localDataSource.getUser()

    override suspend fun getUser(id: Long)= localDataSource.getUser(id)

    override suspend fun getCount() = localDataSource.getCount()

    override suspend fun getUser(keyword: String) = remoteDataSource.getUser(keyword)

    override fun loadUsers() = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 10
        ),
        pagingSourceFactory = { localDataSource.loadUsers() }
    ).flow

    override suspend fun insert(vararg values: UserEntity) = localDataSource.insert(*values)
}