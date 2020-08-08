package com.example.android.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.android.app.data.entity.UserEntity

@Dao
internal interface UserDao {

    @Query("SELECT * FROM users")
    fun observeUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users")
    suspend fun getUser(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?
}