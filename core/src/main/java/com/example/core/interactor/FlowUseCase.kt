package com.example.core.interactor

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<T, in P> where T: Any {

    abstract fun run(params: P): Flow<T>

    operator fun invoke(params: P) = run(params)

}