package com.example.core.interactor
import com.example.core.functional.FlowResult
import kotlinx.coroutines.*

abstract class CoroutineUseCase<out T, out F, in P> where T : Any {

    abstract suspend fun run(params: P): FlowResult<T, F>

    operator fun invoke(
        params: P,
        scope: CoroutineScope = GlobalScope,
        onResult: (FlowResult<T, F>) -> Unit = {}) {
        val job = scope.async(Dispatchers.IO) { run(params) }
        scope.launch(Dispatchers.Main) {
            onResult(job.await())
        }
    }
}
