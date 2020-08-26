package com.example.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.core.database.BaseDao
import com.example.data.entity.UserEntity

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT COUNT(id) FROM users")
    suspend fun getCount(): Int

    @Query("SELECT * FROM users WHERE user_name LIKE :query")
    fun observeUser(query: String): LiveData<List<UserEntity>>

//    @Query("SELECT * FROM users WHERE user_name LIKE :query ORDER BY id ASC")
    @Query("SELECT * FROM users WHERE user_name LIKE :query")
    fun loadUsers(query: String): PagingSource<Int, UserEntity>
}