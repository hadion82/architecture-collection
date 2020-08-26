package com.example.data.datasource.local

import com.example.data.dao.UserDao
import com.example.data.entity.UserEntity

internal class UserLocalDataSourceImpl constructor(
    private val dao: UserDao
) : UserLocalDataSource {

    override suspend fun insert(values: List<UserEntity>) = dao.insert(values)

    override fun observeUsers(query: String) = dao.observeUser("%$query%")

    override fun loadUsers(query: String) = dao.loadUsers("%$query%")
}