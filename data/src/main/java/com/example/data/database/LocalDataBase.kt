package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.UserDao
import com.example.data.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class LocalDataBase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private var INSTANCE: LocalDataBase? = null

        internal fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    LocalDataBase::class.java,
                    "database.db"
                ).build()
            }
    }
}