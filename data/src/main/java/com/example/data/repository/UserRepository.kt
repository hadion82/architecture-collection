package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.entity.UserEntity
import com.example.core.functional.FlowResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun loadUsers(query: String): Flow<PagingData<UserEntity>>
}