package com.example.domain.feature

import com.example.core.functional.Result
import com.example.core.interactor.UseCase
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
): UseCase<Unit, Exception, InsertUserUseCase.Params>() {

    override suspend fun run(params: Params): Result<Unit, Exception> {
        val list = ArrayList<UserEntity>()
        for (i in 1L .. params.max) {
            list.add(UserEntity(i, "name$i", null))
        }
        return repository.insert(*list.toTypedArray())
    }

    class Params(val max: Long)
}