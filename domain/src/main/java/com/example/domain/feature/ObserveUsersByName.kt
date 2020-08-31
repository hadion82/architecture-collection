package com.example.domain.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.core.interactor.LiveUseCase
import com.example.data.core.Failure
import com.example.data.entity.UserEntity
import com.example.data.repository.observe.ObserveRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ObserveUsersByName @Inject constructor(
    private val repository: ObserveRepository
): LiveUseCase<List<UserEntity>, Failure, ObserveUsersByName.Params>() {

    @ExperimentalCoroutinesApi
    override fun run(params: Params) = params.query.switchMap {
        repository.observeUsers(it)
    }

    class Params(val query: MutableLiveData<String>)
}