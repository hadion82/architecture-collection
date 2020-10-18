package com.example.domain.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.example.core.interactor.FlowUseCase
import com.example.data.core.Failure
import com.example.data.entity.UserEntity
import com.example.data.prefs.Common
import com.example.data.repository.DefaultUserRepository
import com.example.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class LoadUserByName @Inject constructor(
    @DefaultUserRepository private val repository: UserRepository,
    private val prefs: Common
): FlowUseCase<Flow<PagingData<UserEntity>>, Failure, LoadUserByName.Params>() {

    @ExperimentalCoroutinesApi
    override fun run(params: Params) = params.query.asFlow()
        .flatMapLatest {
            prefs.searchTime = System.currentTimeMillis()
            repository.loadUsers(it)
        }

    class Params(val query: MutableLiveData<String>)
}