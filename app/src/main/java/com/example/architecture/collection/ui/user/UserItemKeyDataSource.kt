package com.example.architecture.collection.ui.user

import androidx.paging.ItemKeyedDataSource
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository

class UserItemKeyDataSource(
    val repository: UserRepository
): ItemKeyedDataSource<Long, UserEntity>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<UserEntity>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<UserEntity>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<UserEntity>) {
        TODO("Not yet implemented")
    }

    override fun getKey(item: UserEntity): Long {
        TODO("Not yet implemented")
    }
}