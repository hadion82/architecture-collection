package com.example.data.datasource.remote

import com.example.data.core.NetworkFailure
import com.example.data.Data
import com.example.data.entity.UserEntity
import com.example.core.functional.Result

interface UserRemoteDataSource {

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>
}