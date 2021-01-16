package com.example.data.datasource.local.query

import com.example.data.dao.QueryDao
import com.example.data.database.InternalDatabase
import com.example.data.entity.QueryEntity
import javax.inject.Inject

internal class QueryLocalDataSourceImpl @Inject constructor(
    database: InternalDatabase
) : QueryLocalDataSource {

    private val dao: QueryDao = database.getQueryDao()

    override suspend fun insert(vararg values: QueryEntity) = dao.insert(*values)

    override suspend fun deleteByQuery(query: String) = dao.deleteByQuery(query)

    override fun getPage(query: String): Int? = dao.getPage(query)
}