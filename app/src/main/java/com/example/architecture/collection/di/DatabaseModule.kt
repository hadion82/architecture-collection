package com.example.architecture.collection.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                    context,
                    LocalDataBase::class.java,
                    "database.db"
            ).build()

    @Singleton
    @Provides
    fun provideUserDao(dataBase: LocalDataBase) =
            dataBase.getUserDao()
}