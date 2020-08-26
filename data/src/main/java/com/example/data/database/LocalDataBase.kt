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
abstract class LocalDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile private var INSTANCE: LocalDataBase? = null

        internal fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: databaseBuilder(context)
                    .build().also {
                        INSTANCE = it
                    }
            }

        private fun databaseBuilder(context: Context) =
            Room.databaseBuilder(
                context,
                LocalDataBase::class.java,
                "database.db"
            )
    }
}