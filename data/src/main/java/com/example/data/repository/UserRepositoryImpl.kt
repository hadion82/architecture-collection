package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    localDataSourceImpl: UserLocalDataSourceImpl,
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository {

    private val localDataSource: UserLocalDataSource = localDataSourceImpl

    private val remoteDataSource: UserRemoteDataSource = remoteDataSourceImpl

    override fun observeUsers(keyword: String): LiveData<FlowResult<List<UserEntity>, NetworkFailure>> = liveData {
        emitSource(
            localDataSource.observeUsers(keyword)
                .map { FlowResult.Success(it) }
        )
        val result = flowResult { remoteDataSource.searchUser(keyword) }
        result.onSuccess { localDataSource.insert(it.items) }
    }

    override suspend fun searchUser(keyword: String) = remoteDataSource.searchUser(keyword)

    override fun loadUsers(keyword: String): Flow<FlowResult<Flow<PagingData<UserEntity>>, NetworkFailure.Exception>> =
        flow {
            try {
                emit(FlowResult.Loading)
                emit(
                    FlowResult.Success(
                        Pager(
                            config = PagingConfig(
                                pageSize = 10,
                                enablePlaceholders = false,
                                prefetchDistance = 10
                            ),
                            pagingSourceFactory = { localDataSource.loadUsers(keyword) }
                        ).flow
                    )
                )
                val result = flowResult { remoteDataSource.searchUser(keyword) }
                result.onSuccess { localDataSource.insert(it.items) }
                    .onFailure { emit(FlowResult.Failure(NetworkFailure.Exception(it))) }

            } catch (e: Exception) {
                emit(
                    FlowResult.Failure(NetworkFailure.Exception(e))
                )
            }
        }
}