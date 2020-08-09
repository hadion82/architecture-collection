package com.example.data.api

import com.example.data.Data
import com.example.data.entity.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/users")
    fun getUser(@Query(value = "q") keyword: String): Data<UserEntity>

}