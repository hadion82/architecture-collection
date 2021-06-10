package com.example.core.interactor

import com.example.core.functional.FlowResult
import kotlinx.coroutines.*
import kotlin.Exception

abstract class CoroutineUseCase<in P, out T>(
    private val coroutineDispatcher: CoroutineDispatcher
) where T : Any {

    abstract suspend fun execute(params: P): T

    suspend operator fun invoke(
        params: P
    ) = try {
        withContext(coroutineDispatcher) {
            execute(params).let {
                FlowResult.Success(it)
            }
        }
    } catch (e: Exception) {
        FlowResult.Failure(e)
    }
}
