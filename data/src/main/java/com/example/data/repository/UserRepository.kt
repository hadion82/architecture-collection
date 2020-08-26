package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.data.core.NetworkFailure
import com.example.data.GithubResponse
import com.example.data.entity.UserEntity
import com.example.core.functional.FlowResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUser(keyword: String): GithubResponse<UserEntity>

    fun observeUsers(keyword: String): LiveData<FlowResult<List<UserEntity>, NetworkFailure>>

    fun loadUsers(keyword: String): Flow<FlowResult<Flow<PagingData<UserEntity>>, NetworkFailure>>
}