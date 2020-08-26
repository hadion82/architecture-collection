package com.example.core.interactor

import com.example.core.functional.FlowResult
import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<out T, out F, in P> where T: Any {

    abstract fun run(params: P): Flow<FlowResult<T, F>>

    operator fun invoke(params: P) = run(params)

}