package com.example.android.app.data.datasource.local

import androidx.lifecycle.LiveData
import com.example.android.app.core.NetworkFailure
import com.example.android.app.data.entity.UserEntity
import com.example.core.functional.Result

internal interface UserLocalDataSource {

    fun observeUser(): LiveData<Result<List<UserEntity>, Exception>>

    suspend fun getUser(): Result<List<UserEntity>, Exception>

    suspend fun getUser(id: Long): Result<UserEntity?, Exception>
}
