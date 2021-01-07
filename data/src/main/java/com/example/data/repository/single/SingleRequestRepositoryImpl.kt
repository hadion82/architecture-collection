package com.example.data.repository.single

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.functional.FlowResult
import com.example.core.functional.flowResult
import com.example.core.functional.onFailure
import com.example.core.functional.onSuccess
import com.example.data.core.NetworkFailure
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SingleRequestRepositoryImpl @Inject internal constructor(
    localDataSourceImpl: UserLocalDataSourceImpl,
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository {

    private val localDataSource: UserLocalDataSource = localDataSourceImpl

    private val remoteDataSource: UserRemoteDataSource = remoteDataSourceImpl

    override suspend fun loadUsers(query: String): Flow<PagingData<UserEntity>> {
        val response = remoteDataSource.searchUser(query)
        localDataSource.insert(response.items)
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                localDataSource.loadUsers(query)
            }
        ).flow
    }
}