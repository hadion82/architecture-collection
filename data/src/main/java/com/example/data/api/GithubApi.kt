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

interface GithubApi {

    @GET("/search/users")
    suspend fun searchUser(@Query(value = "q") keyword: String): GithubResponse<UserEntity>

    companion object {

        private fun createOkHttpClient() =
            OkHttpClient.Builder().apply {
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
                connectTimeout(10, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            }.build()

        private fun createGitHubRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        fun create(): GithubApi =
            createGitHubRetrofit(
                createOkHttpClient()
            ).create(GithubApi::class.java)
    }

}