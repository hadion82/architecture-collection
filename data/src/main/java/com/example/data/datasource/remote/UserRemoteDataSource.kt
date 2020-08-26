package com.example.data.datasource.remote

import com.example.data.GithubResponse
import com.example.data.entity.UserEntity

interface UserRemoteDataSource {

    suspend fun searchUser(keyword: String): GithubResponse<UserEntity>
}