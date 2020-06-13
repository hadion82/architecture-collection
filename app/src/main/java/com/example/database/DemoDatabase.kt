package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.DemoDatabase.Companion.DB_VERSION
import com.example.database.dao.UserDao
import com.example.database.entity.User

@Database(
    entities = [User::class],
    version = DB_VERSION
)
abstract class DemoDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private const val DB_NAME = "demo.db"
        const val DB_VERSION = 1
        private var INSTANCE: DemoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DemoDatabase {
            if (INSTANCE == null) {
                synchronized(DemoDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        DemoDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE as DemoDatabase
        }
    }
}