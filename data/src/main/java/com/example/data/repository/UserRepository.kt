package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    @Throws(Exception::class)
    suspend fun loadUsers(query: String, isRefresh: Boolean): Flow<PagingData<UserEntity>>
}