package com.example.data.datasource.remote

import com.example.data.GithubResponse
import com.example.data.entity.UserEntity

internal interface UserRemoteDataSource {

    suspend fun searchUser(query: String): GithubResponse<UserEntity>
}