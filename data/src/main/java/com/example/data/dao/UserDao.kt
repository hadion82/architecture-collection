package com.example.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.core.database.BaseDao
import com.example.data.entity.UserEntity

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT * FROM users")
    fun observeUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users")
    suspend fun getUser(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT COUNT(id) FROM users")
    suspend fun getCount(): Int

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun loadUsers(): PagingSource<Int, UserEntity>
}