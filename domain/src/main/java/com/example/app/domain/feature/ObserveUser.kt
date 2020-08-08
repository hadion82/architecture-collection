package com.example.app.domain.feature

import com.example.android.app.data.entity.UserEntity
import com.example.android.app.data.repository.UserRepository
import com.example.core.functional.Result
import com.example.core.interactor.LiveUseCase
import javax.inject.Inject

class ObserveUser @Inject constructor(
        private val repository: UserRepository
): LiveUseCase<List<UserEntity>, ObserveUser.Params>() {

    override fun run(params: Params) = repository.observeUser()

    class Params
}