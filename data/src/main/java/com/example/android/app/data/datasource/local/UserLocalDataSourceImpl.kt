package com.example.android.app.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.android.app.data.dao.UserDao
import com.example.android.app.data.datasource.DataSourceImpl
import com.example.android.app.data.entity.UserEntity
import com.example.core.functional.Result

internal class UserLocalDataSourceImpl(
    private val dao: UserDao
) : DataSourceImpl(),
    UserLocalDataSource {

    override fun observeUser():
            LiveData<Result<List<UserEntity>, Exception>> =
        dao.observeUser().map { Result.Success(it) }

    override suspend fun getUser() = catchResult { dao.getUser() }

    override suspend fun getUser(id: Long) = catchResult { dao.getUser(id) }
}