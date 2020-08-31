package com.example.data.api

import com.example.data.GithubResponse
import com.example.data.entity.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GithubApi {

    @GET("/search/users")
    suspend fun searchUser(@Query(value = "q") query: String): GithubResponse<UserEntity>

    @GET("/search/users")
    suspend fun searchUser(
        @Query(value = "q") query: String,
        @Query(value = "page") page: Int
    ): Response<GithubResponse<UserEntity>>
}