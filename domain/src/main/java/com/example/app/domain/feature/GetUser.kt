package com.example.app.domain.feature

import com.example.android.app.data.Data
import com.example.android.app.data.entity.UserEntity
import com.example.android.app.data.repository.UserRepository
import com.example.app.domain.core.interactor.NetworkUseCase
import javax.inject.Inject

class GetUser
    @Inject constructor(
            private val repository: UserRepository
    ): NetworkUseCase<Data<UserEntity>, GetUser.Params>() {

    override suspend fun run(params: Params) = repository.getUser(params.keyword)

    data class Params(val keyword: String)
}