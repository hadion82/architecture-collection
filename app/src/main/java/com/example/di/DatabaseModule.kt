package com.example.di

import android.content.Context
import com.example.database.DemoDatabase
import com.example.database.dao.UserDao
import com.example.database.persistence.UserDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): DemoDatabase =
        DemoDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideUserDao(database: DemoDatabase): UserDao =
        database.getUserDao()

    @Singleton
    @Provides
    fun provideUserDataSource(userDao: UserDao): UserDataSource =
        UserDataSource(userDao)
}