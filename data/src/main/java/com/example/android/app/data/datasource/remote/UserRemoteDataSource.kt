package com.example.android.app.data.datasource.remote

import com.example.android.app.core.NetworkFailure
import com.example.android.app.data.Data
import com.example.android.app.data.entity.UserEntity
import com.example.core.functional.Result

interface UserRemoteDataSource {

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>
}