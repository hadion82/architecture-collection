package com.example.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.data.entity.UserEntity
import com.example.core.functional.Result
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>

    suspend fun getCount(): Result<Int, Exception>

    suspend fun insert(vararg t: UserEntity): Result<Unit, Exception>

    fun observeUser(): LiveData<List<UserEntity>>

    fun loadUsers(): PagingSource <Int, UserEntity>
}
