package com.example.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.core.database.BaseDao
import com.example.data.entity.UserEntity

@Dao
internal interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT * FROM users WHERE `user_query` = :query")
    fun observeUser(query: String): LiveData<List<UserEntity>>

//    @Query("SELECT * FROM users WHERE user_name LIKE :query ORDER BY id DESC")
    @Query("SELECT * FROM users WHERE `user_query` = :query")
    fun loadUsers(query: String): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users WHERE `user_query` = :query")
    suspend fun deleteByQuery(query: String)
}