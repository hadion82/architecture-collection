package com.example.android.app.data.repository

import androidx.lifecycle.LiveData
import com.example.android.app.core.NetworkFailure
import com.example.android.app.data.Data
import com.example.android.app.data.entity.UserEntity
import com.example.core.functional.Result

interface UserRepository {

    fun observeUser(): LiveData<Result<List<UserEntity>, Exception>>

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>
}