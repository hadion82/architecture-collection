package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.UserDao
import com.example.data.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class LocalDataBase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
}