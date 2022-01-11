package com.example.core.interactor

import com.example.core.functional.FlowResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, out T>(
    private val coroutineDispatcher: CoroutineDispatcher
) where T: Any {

    @OptIn(InternalCoroutinesApi::class)
    abstract fun execute(params: P): Flow<FlowResult<T>>

    @OptIn(InternalCoroutinesApi::class)
    operator fun invoke(params: P) = execute(params)
        .catch { FlowResult.Failure(Exception(it)) }
        .flowOn(coroutineDispatcher)

}