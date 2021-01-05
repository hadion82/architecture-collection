package com.example.data.repository.memory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.functional.FlowResult
import com.example.data.core.NetworkFailure
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemoryRepositoryImpl @Inject internal constructor(
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository {

    private val remote: UserRemoteDataSource = remoteDataSourceImpl

    override suspend fun loadUsers(query: String): Flow<PagingData<UserEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = {
                MemoryPagingSource(
                    remote,
                    query
                )
            }
        ).flow
}