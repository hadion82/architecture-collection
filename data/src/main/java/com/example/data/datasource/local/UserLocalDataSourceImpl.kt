package com.example.data.datasource.local

import com.example.data.dao.UserDao
import com.example.data.database.EncapsulationDatabase
import com.example.data.entity.UserEntity
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    database: EncapsulationDatabase
) : UserLocalDataSource {

    private val dao: UserDao = database.getUserDao()

    override suspend fun insert(values: List<UserEntity>) = dao.insert(values)

    override fun observeUsers(query: String) = dao.observeUser("%$query%")

    override fun loadUsers(query: String) = dao.loadUsers("%$query%")
}