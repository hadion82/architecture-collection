package com.example.data.datasource.remote

import androidx.paging.PagingData
import com.example.data.core.NetworkFailure
import com.example.data.Data
import com.example.data.entity.UserEntity
import com.example.core.functional.Result
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>
}