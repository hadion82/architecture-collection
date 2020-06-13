package com.example.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.database.entity.User

@Dao
interface UserDao: ReactiveDao<User> {

    @Query("SELECT * from users WHERE id = :userId")
    fun getUser(userId: Long): LiveData<User?>

    @Query("SELECT * from users")
    fun getUsers(): LiveData<List<User>>
}