package com.example.android.app.data.datasource.remote

import com.example.android.app.data.api.GithubApi
import com.example.android.app.data.datasource.DataSourceImpl

class UserRemoteDataSourceImpl(
    private val service: GithubApi
): DataSourceImpl(), UserRemoteDataSource {

    override suspend fun getUser(keyword: String)
            = catchNetworkResult { service.getUser(keyword) }
}