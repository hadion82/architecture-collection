package com.example.data.repository.memory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserMemoryRepositoryImpl @Inject internal constructor(
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository {

    private val remote: UserRemoteDataSource = remoteDataSourceImpl

    override suspend fun loadUsers(query: String, isRefresh: Boolean): Flow<PagingData<UserEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                UserMemoryPagingSource(
                    remote,
                    query
                )
            }
        ).flow
}