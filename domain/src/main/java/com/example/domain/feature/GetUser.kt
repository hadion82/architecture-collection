package com.example.domain.feature

import com.example.data.Data
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import com.example.domain.core.interactor.NetworkUseCase
import javax.inject.Inject

class GetUser
    @Inject constructor(
            private val repository: UserRepository
    ): NetworkUseCase<Data<UserEntity>, GetUser.Params>() {

    override suspend fun run(params: Params) = repository.getUser(params.keyword)

    data class Params(val keyword: String)
}