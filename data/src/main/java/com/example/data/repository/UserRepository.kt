package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.data.core.NetworkFailure
import com.example.data.Data
import com.example.data.entity.UserEntity
import com.example.core.functional.Result

interface UserRepository {

    fun observeUser(): LiveData<Result<List<UserEntity>, Exception>>

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>

    suspend fun getCount(): Result<Int, Exception>

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>

    fun loadUsers(): DataSource.Factory <Int, UserEntity>
}