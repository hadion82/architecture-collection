package com.example.data.repository.db

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRequestRepositoryImpl @Inject internal constructor(
    localDataSourceImpl: UserLocalDataSourceImpl
) : UserRepository {

    private val localDataSource: UserLocalDataSource = localDataSourceImpl


    override suspend fun loadUsers(query: String, refresh: Boolean): Flow<PagingData<UserEntity>> {
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