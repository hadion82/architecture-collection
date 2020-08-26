package com.example.core.functional

sealed class FlowResult<out T, out F> {

    data class Success<out T>(val value: T) : FlowResult<T, Nothing>()
    data class Failure<out F>(val failure: F) : FlowResult<Nothing, F>()
    object Loading : FlowResult<Nothing, Nothing>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure<F>

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$value]"
            is Failure -> "Error[exception=$failure]"
            is Loading -> "Loading"
        }
    }


}

fun <T, F> FlowResult<T, F>.subscribe(onResult: (T) -> Any, onFailure: (F) -> Any): Any =
    when (this) {
        is FlowResult.Success -> onResult(value)
        is FlowResult.Failure -> onFailure(failure)
        is FlowResult.Loading -> {
        }
    }

fun <T, F> FlowResult<T, F>.subscribe(onResult: (T?, F?) -> Any): Any =
    when (this) {
        is FlowResult.Success -> onResult(value, null)
        is FlowResult.Failure -> onResult(null, failure)
        is FlowResult.Loading -> {
        }
    }

fun <F> FlowResult<Unit, F>.complete(onResult: () -> Any, onFailure: (F) -> Any): Any =
    when (this) {
        is FlowResult.Success -> onResult()
        is FlowResult.Failure -> onFailure(failure)
        is FlowResult.Loading -> {
        }
    }

inline fun <reified T, reified F> FlowResult<T, F>.onSuccess(
    func: (T) -> Unit
): FlowResult<T, F> = apply {
    if (this is FlowResult.Success) func(this.value)
}

inline fun <reified T, reified F> FlowResult<T, F>.onFailure(
    f: (F) -> Unit
): FlowResult<T, F> = apply {
    if (this is FlowResult.Failure) f(this.failure)
}

inline fun <reified V, reified F, reified R> FlowResult<V, F>.flatMap(f: (V) -> FlowResult<R, F>): FlowResult<R, F> =
    when (this) {
        is FlowResult.Success -> f(value)
        is FlowResult.Failure -> FlowResult.Failure(failure)
        is FlowResult.Loading -> FlowResult.Loading
    }

inline fun <reified V, reified F, reified R> FlowResult<V, F>.map(f: (V) -> (R)): FlowResult<R, F> =
    when (this) {
        is FlowResult.Success -> FlowResult.Success(f(value))
        is FlowResult.Failure -> FlowResult.Failure(failure)
        is FlowResult.Loading -> FlowResult.Loading
    }

inline fun <reified V, reified F, reified R, reified C> FlowResult<V, F>.zipWith(
    result: FlowResult<R, F>,
    f: (V, R) -> (C)
): FlowResult<C, F> =
    when (this) {
        is FlowResult.Success -> when (result) {
            is FlowResult.Success -> FlowResult.Success(f(value, result.value))
            is FlowResult.Failure -> FlowResult.Failure(result.failure)
            is FlowResult.Loading -> FlowResult.Loading
        }
        is FlowResult.Failure -> FlowResult.Failure(failure)
        is FlowResult.Loading -> FlowResult.Loading
    }

suspend fun <T> flowResult(block: suspend () -> T) = try {
    val result = block()
    FlowResult.Success(result)
} catch (e: Exception) {
    FlowResult.Failure(e)
}
