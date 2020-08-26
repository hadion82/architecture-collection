package com.example.data

import android.content.Context
import com.example.data.api.GithubApi
import com.example.data.database.LocalDataBase
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl

object RepositoryLocator {

    fun createUserRepository(context: Context): UserRepository =
        UserRepositoryImpl(
            createUserLocalDataSource(context),
            createUserRemoteDataSource()
        )

    private fun createUserLocalDataSource(context: Context): UserLocalDataSource =
        UserLocalDataSourceImpl(
            LocalDataBase.getInstance(context).getUserDao()
        )

    private fun createUserRemoteDataSource(): UserRemoteDataSource =
        UserRemoteDataSourceImpl(
            GithubApi.create()
        )
}