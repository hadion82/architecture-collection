package com.example.data.api

import com.example.data.GithubResponse
import com.example.data.entity.UserEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

internal interface GithubApi {

    @GET("/search/users")
    suspend fun searchUser(@Query(value = "q") query: String): GithubResponse<UserEntity>
}