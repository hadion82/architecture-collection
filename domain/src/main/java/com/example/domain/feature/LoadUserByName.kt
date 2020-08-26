package com.example.domain.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.example.core.interactor.FlowCase
import com.example.core.interactor.FlowUseCase
import com.example.data.core.NetworkFailure
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class LoadUserByName @Inject constructor(
    private val repository: UserRepository
): FlowUseCase<Flow<PagingData<UserEntity>>, NetworkFailure, LoadUserByName.Params>() {

    @ExperimentalCoroutinesApi
    override fun run(params: Params) = params.query.asFlow()
        .flatMapLatest { repository.loadUsers(it) }

    class Params(val query: MutableLiveData<String>)
}