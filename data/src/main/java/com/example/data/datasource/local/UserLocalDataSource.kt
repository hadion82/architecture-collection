package com.example.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.data.entity.UserEntity
import com.example.core.functional.Result

interface UserLocalDataSource {

    fun observeUser(): LiveData<Result<List<UserEntity>, Exception>>

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>

    suspend fun getCount(): Result<Int, Exception>

    fun loadUsers(): DataSource.Factory <Int, UserEntity>
}
