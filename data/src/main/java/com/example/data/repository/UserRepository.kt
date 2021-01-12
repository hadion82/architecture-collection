package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.entity.UserEntity
import com.example.core.functional.FlowResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loadUsers(query: String, refresh: Boolean): Flow<PagingData<UserEntity>>
}