package com.example.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.data.entity.UserEntity

internal interface UserLocalDataSource {

    suspend fun insert(values: List<UserEntity>)

    suspend fun deleteByQuery(query: String)

    fun observeUsers(query: String): LiveData<List<UserEntity>>

    fun loadUsers(query: String): PagingSource <Int, UserEntity>
}
