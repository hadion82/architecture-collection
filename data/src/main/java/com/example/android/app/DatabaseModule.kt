package com.example.android.app

import android.content.Context
import androidx.room.Room
import com.example.android.app.data.database.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@Singleton
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    internal fun provideDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                    context,
                    LocalDataBase::class.java,
                    "local_database.db"
            ).build()

    @Provides
    internal fun provideUserDao(dataBase: LocalDataBase) =
            dataBase.getUserDao()
}