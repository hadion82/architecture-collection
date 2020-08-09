package com.example

import android.app.Application
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
class AndroidApplication: Application()