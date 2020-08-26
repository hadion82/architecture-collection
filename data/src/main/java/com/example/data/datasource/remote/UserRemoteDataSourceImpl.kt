package com.example.data.datasource.remote

import com.example.data.api.GithubApi

internal class UserRemoteDataSourceImpl (
    private val service: GithubApi
): UserRemoteDataSource {

    override suspend fun searchUser(keyword: String)
            = service.searchUser(keyword)
}