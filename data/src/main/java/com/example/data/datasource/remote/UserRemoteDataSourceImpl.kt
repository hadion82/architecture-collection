package com.example.data.datasource.remote

import com.example.data.GithubResponse
import com.example.data.api.InternalGithubApi
import com.example.data.api.GithubApi
import com.example.data.entity.UserEntity
import retrofit2.Response
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    encapsulation: InternalGithubApi
) : UserRemoteDataSource {

    private val service: GithubApi = encapsulation()

    override suspend fun searchUser(query: String) = service.searchUser(query)

    override suspend fun searchUser(
        query: String,
        page: Int
    ): Response<GithubResponse<UserEntity>> =
        service.searchUser(query, page)
}