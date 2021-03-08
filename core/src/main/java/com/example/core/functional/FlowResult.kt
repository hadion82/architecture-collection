package com.example.core.functional

sealed class FlowResult<out T> {

    data class Success<out T>(val value: T) : FlowResult<T>()
    data class Failure(val exception: Exception) : FlowResult<Nothing>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$value]"
            is Failure -> "Error[exception=$exception]"
        }
    }
}

inline fun <T, R> FlowResult<T>.subscribe(onSuccess: (T) -> R, onFailure: (Exception) -> R): R =
    when (this) {
        is FlowResult.Success -> onSuccess(value)
        is FlowResult.Failure -> onFailure(exception)
    }

fun FlowResult<Unit>.complete(onResult: () -> Any, onFailure: (Exception) -> Any): Any =
    when (this) {
        is FlowResult.Success -> onResult()
        is FlowResult.Failure -> onFailure(exception)
    }

inline fun <reified T> FlowResult<T>.onSuccess(
    func: (T) -> Unit
): FlowResult<T> = apply {
    if (this is FlowResult.Success) func(this.value)
}

inline fun <reified T> FlowResult<T>.onFailure(
    f: (Exception) -> Unit
): FlowResult<T> = apply {
    if (this is FlowResult.Failure) f(this.exception)
}

inline fun <reified V, reified F, reified R> FlowResult<V>.flatMap(f: (V) -> FlowResult<R>): FlowResult<R> =
    when (this) {
        is FlowResult.Success -> f(value)
        is FlowResult.Failure -> FlowResult.Failure(exception)
    }

inline fun <reified V, reified F, reified R> FlowResult<V>.map(fn: (V) -> (R)): FlowResult<R> =
    when (this) {
        is FlowResult.Success -> FlowResult.Success(fn(value))
        is FlowResult.Failure -> FlowResult.Failure(exception)
    }

inline fun <reified V, reified F, reified R, reified C> FlowResult<V>.zipWith(
    result: FlowResult<R>,
    f: (V, R) -> (C)
): FlowResult<C> =
    when (this) {
        is FlowResult.Success -> when (result) {
            is FlowResult.Success -> FlowResult.Success(f(value, result.value))
            is FlowResult.Failure -> FlowResult.Failure(result.exception)
        }
        is FlowResult.Failure -> FlowResult.Failure(exception)
    }

suspend fun <T> flowResult(block: suspend () -> T) = try {
    val result = block()
    FlowResult.Success(result)
} catch (e: Exception) {
    FlowResult.Failure(e)
}
