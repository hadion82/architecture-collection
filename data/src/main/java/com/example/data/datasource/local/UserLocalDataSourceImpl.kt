package com.example.data.datasource.local

import androidx.lifecycle.LiveData
import com.example.data.dao.UserDao
import com.example.data.datasource.DataSourceImpl
import com.example.data.entity.UserEntity

internal class UserLocalDataSourceImpl constructor(
    private val dao: UserDao
) : DataSourceImpl(),
    UserLocalDataSource {

    override suspend fun getUser() = catchResult { dao.getUser() }

    override suspend fun getUser(id: Long) = catchResult { dao.getUser(id) }

    override suspend fun getCount() = catchResult { dao.getCount() }

    override suspend fun insert(vararg values: UserEntity) = catchResult { dao.insert(*values) }

    override fun observeUser(): LiveData<List<UserEntity>> = dao.observeUser()

    override fun loadUsers() = dao.loadUsers()
}