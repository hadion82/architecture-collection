package com.example.architecture.collection.di

import android.content.Context
import com.example.data.database.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseModule(@ApplicationContext context: Context): LocalDataBase =
        LocalDataBase.getInstance(context)
}