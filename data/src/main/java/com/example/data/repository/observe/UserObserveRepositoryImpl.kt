package com.example.data.repository.observe

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.core.functional.FlowResult
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import javax.inject.Inject

class UserObserveRepositoryImpl @Inject internal constructor(
    localDataSourceImpl: UserLocalDataSourceImpl,
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserObserveRepository {

    private val localDataSource: UserLocalDataSource = localDataSourceImpl

    private val remoteDataSource: UserRemoteDataSource = remoteDataSourceImpl

    override fun observeUsers(query: String): LiveData<FlowResult<List<UserEntity>>> =
        liveData {
            emitSource(
                localDataSource.observeUsers(query)
                    .map { FlowResult.Success(it) }
            )
            val response = remoteDataSource.searchUser(query)
            localDataSource.insert(response.items)
        }

}