package com.example.data.datasource.remote

import com.example.data.api.EncapsulationGithubApi
import com.example.data.api.GithubApi
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    encapsulation: EncapsulationGithubApi
): UserRemoteDataSource {

    private val service: GithubApi = encapsulation()

    override suspend fun searchUser(query: String)
            = service.searchUser(query)
}