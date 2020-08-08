package com.example.android.app.data.database

import androidx.room.RoomDatabase
import com.example.android.app.data.dao.UserDao

internal abstract class LocalDataBase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
}