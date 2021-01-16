package com.example.domain.feature

import com.example.core.interactor.LiveUseCase
import com.example.data.entity.UserEntity
import com.example.data.repository.observe.UserObserveRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ObserveUsersByName @Inject constructor(
    private val repository: UserObserveRepository
): LiveUseCase<ObserveUsersByName.Params, List<UserEntity>>() {

    @ExperimentalCoroutinesApi
    override fun execute(params: Params) = repository.observeUsers(params.query)

    class Params(val query: String)
}