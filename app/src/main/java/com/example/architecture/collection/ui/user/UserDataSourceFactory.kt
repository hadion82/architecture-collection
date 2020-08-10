package com.example.architecture.collection.ui.user

import androidx.paging.DataSource
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository

class UserDataSourceFactory(
    private val userDataRepository: UserRepository
): DataSource.Factory<Long, UserEntity>() {

    override fun create(): DataSource<Long, UserEntity> =
        UserItemKeyDataSource(
            userDataRepository
        )
}