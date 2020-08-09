package com.example.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.example.data.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun observeUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users")
    suspend fun getUser(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT COUNT(id) FROM users")
    suspend fun getCount(): Int

    @Query("SELECT * FROM users")
    fun loadUsers(): DataSource.Factory <Int, UserEntity>
}