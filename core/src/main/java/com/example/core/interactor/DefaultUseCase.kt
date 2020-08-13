package com.example.core.interactor

import com.example.core.functional.Result

class DefaultUseCase<out T, in P>: UseCase<T, Exception, P>() where T: Any {

    override suspend fun run(params: P): Result<T, Exception> {
        TODO("Not yet implemented")
    }
}