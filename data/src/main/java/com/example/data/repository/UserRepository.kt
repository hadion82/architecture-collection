package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingData
import com.example.data.core.NetworkFailure
import com.example.data.Data
import com.example.data.entity.UserEntity
import com.example.core.functional.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>

    suspend fun getCount(): Result<Int, Exception>

    suspend fun getUser(keyword: String): Result<Data<UserEntity>, NetworkFailure>

    suspend fun insert(vararg values: UserEntity): Result<Unit, Exception>

    fun observeUser(): LiveData<List<UserEntity>>

    fun loadUsers(): Flow<PagingData<UserEntity>>
}