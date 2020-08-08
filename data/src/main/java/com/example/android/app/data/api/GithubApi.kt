package com.example.android.app.data.api

import com.example.android.app.data.Data
import com.example.android.app.data.entity.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/users")
    fun getUser(@Query(value = "q") keyword: String): Data<UserEntity>

}