package com.example.data.data

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.data.dao.QueryDao
import com.example.data.dao.UserDao
import com.example.data.database.LocalDataBase
import org.mockito.Mockito.mock

class FakeLocalDatabase : LocalDataBase() {

    override fun getUserDao(): UserDao =
        mock(UserDao::class.java)

    override fun getQueryDao(): QueryDao =
        mock(QueryDao::class.java)

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper =
        mock(SupportSQLiteOpenHelper::class.java)

    override fun createInvalidationTracker(): InvalidationTracker =
        mock(InvalidationTracker::class.java)

    override fun clearAllTables() {}
}