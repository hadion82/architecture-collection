package com.example.data.datasource.local.query

import com.example.data.dao.QueryDao
import com.example.data.dao.UserDao
import com.example.data.database.EncapsulationDatabase
import com.example.data.entity.QueryEntity
import com.example.data.entity.UserEntity
import javax.inject.Inject

internal class QueryLocalDataSourceImpl @Inject constructor(
    database: EncapsulationDatabase
) : QueryLocalDataSource {

    private val dao: QueryDao = database.getQueryDao()

    override suspend fun insert(vararg values: QueryEntity) = dao.insert(*values)

    override fun getPage(query: String): Int? = dao.getPage(query)
}