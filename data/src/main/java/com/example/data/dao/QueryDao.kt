package com.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.core.database.BaseDao
import com.example.data.entity.QueryEntity
import com.example.data.entity.UserEntity

@Dao
internal interface QueryDao: BaseDao<QueryEntity> {

    @Query("SELECT page FROM user_query WHERE `name_query` = :query")
    fun getPage(query: String): Int?

    @Query("DELETE FROM user_query WHERE `name_query` = :query")
    suspend fun deleteByQuery(query: String)
}