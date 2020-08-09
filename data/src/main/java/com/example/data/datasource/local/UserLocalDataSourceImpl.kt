package com.example.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.data.dao.UserDao
import com.example.data.datasource.DataSourceImpl
import com.example.data.entity.UserEntity
import com.example.core.functional.Result

class UserLocalDataSourceImpl(
    private val dao: UserDao
) : DataSourceImpl(),
    UserLocalDataSource {

    override fun observeUser():
            LiveData<Result<List<UserEntity>, Exception>> =
        dao.observeUser().map { Result.Success(it) }

    override suspend fun getUser() = catchResult { dao.getUser() }

    override suspend fun getUser(id: Long) = catchResult { dao.getUser(id) }

    override fun loadUsers() = dao.loadUsers()

    override suspend fun getCount() = catchResult { dao.getCount() }
}