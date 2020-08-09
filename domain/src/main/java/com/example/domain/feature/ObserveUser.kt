package com.example.domain.feature

import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import com.example.core.interactor.LiveUseCase
import javax.inject.Inject

class ObserveUser @Inject constructor(
        private val repository: UserRepository
): LiveUseCase<List<UserEntity>, ObserveUser.Params>() {

    override fun run(params: Params) = repository.observeUser()

    class Params
}