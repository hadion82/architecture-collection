package com.example.data.datasource.remote

import com.example.data.GithubResponse
import com.example.data.entity.UserEntity
import retrofit2.Response

internal interface UserRemoteDataSource {

    suspend fun searchUser(query: String): GithubResponse<UserEntity>

    suspend fun searchUser(query: String, page: Int): Response<GithubResponse<UserEntity>>
}