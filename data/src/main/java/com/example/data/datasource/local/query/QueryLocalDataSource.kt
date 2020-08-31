package com.example.data.datasource.local.query

import com.example.data.entity.QueryEntity

internal interface QueryLocalDataSource {

    suspend fun insert(vararg values: QueryEntity)

    fun getPage(query: String): Int?
}