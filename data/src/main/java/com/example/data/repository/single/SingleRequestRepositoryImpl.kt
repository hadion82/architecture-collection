package com.example.data.repository.single

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.functional.FlowResult
import com.example.core.functional.flowResult
import com.example.core.functional.onFailure
import com.example.core.functional.onSuccess
import com.example.data.core.Failure
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

    override fun loadUsers(query: String): Flow<FlowResult<Flow<PagingData<UserEntity>>, Failure>> =
        flow {
            try {
                emit(FlowResult.Loading)
                val result = flowResult { remoteDataSource.searchUser(query) }
                result.onSuccess {
                    localDataSource.insert(it.items)
                    emit(
                        FlowResult.Success(
                            Pager(
                                config = PagingConfig(
                                    pageSize = 30,
                                    enablePlaceholders = false,
                                    prefetchDistance = 10
                                ),
                                pagingSourceFactory = {
                                    localDataSource.loadUsers(query)
                                }
                            ).flow
                        )
                    )
                }.onFailure {
                    emit(FlowResult.Failure(Failure.Exception(it)))
                }

            } catch (e: Exception) {
                emit(
                    FlowResult.Failure(Failure.Exception(e))
                )
            }
        }
}