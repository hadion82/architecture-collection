package com.example.architecture.collection.di

import com.example.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GitHubApiService

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherApiService

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

    @Singleton
    @GitHubApiService
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideGitHubService(@GitHubApiService retrofit: Retrofit) =
        retrofit.create(GithubApi::class.java)
}