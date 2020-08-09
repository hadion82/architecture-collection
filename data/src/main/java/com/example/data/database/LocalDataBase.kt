package com.example.data.database

import androidx.room.RoomDatabase
import com.example.data.dao.UserDao

abstract class LocalDataBase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
}