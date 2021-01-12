package com.example.domain.feature

import androidx.paging.PagingData
import com.example.core.di.IoDispatcher
import com.example.core.interactor.CoroutineUseCase
import com.example.data.entity.UserEntity
import com.example.data.repository.DefaultUserRepository
import com.example.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadUserByName @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultUserRepository private val repository: UserRepository,
): CoroutineUseCase<LoadUserByName.Params, Flow<PagingData<UserEntity>>>(ioDispatcher) {

    @InternalCoroutinesApi
    override suspend fun execute(params: Params) = repository.loadUsers(params.query, params.refresh)

    class Params(val query: String, val refresh: Boolean = false)
}