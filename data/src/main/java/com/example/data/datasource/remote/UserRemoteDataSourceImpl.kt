package com.example.data.datasource.remote

import com.example.data.api.GithubApi
import com.example.data.datasource.DataSourceImpl
import javax.inject.Inject

internal class UserRemoteDataSourceImpl (
    private val service: GithubApi
): DataSourceImpl(), UserRemoteDataSource {

    override suspend fun getUser(keyword: String)
            = catchNetworkResult { service.getUser(keyword) }
}