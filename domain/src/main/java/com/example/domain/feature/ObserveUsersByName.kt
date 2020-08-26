package com.example.domain.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.core.interactor.LiveUseCase
import com.example.data.core.NetworkFailure
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ObserveUsersByName @Inject constructor(
    private val repository: UserRepository
): LiveUseCase<List<UserEntity>, NetworkFailure, ObserveUsersByName.Params>() {

    @ExperimentalCoroutinesApi
    override fun run(params: Params) = params.query.switchMap {
        repository.observeUsers(it)
    }

    class Params(val query: MutableLiveData<String>)
}