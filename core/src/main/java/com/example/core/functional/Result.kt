package com.example.core.functional

sealed class Result<out T, out F : Exception> {

    data class Success<out T>(val value: T) : Result<T, Nothing>()
    data class Failure<out F : Exception>(val failure: F) : Result<Nothing, F>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure<F>

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$value]"
            is Failure -> "Error[exception=$failure]"
        }
    }

    fun dispatch(onResult: (T) -> Any, onFailure: (F) -> Any): Any =
        when (this) {
            is Success -> onResult(value)
            is Failure -> onFailure(failure)
        }
}

inline fun <reified T, reified F : Exception> Result<T, F>.onSuccess(
    func: (T) -> Unit
): Result<T, F> = apply {
    if (this is Result.Success) func(this.value)
}

inline fun <reified T, reified F : Exception> Result<T, F>.onFailure(
    f: (F) -> Unit
): Result<T, F> = apply {
    if (this is Result.Failure) f(this.failure)
}

inline fun <reified V, reified F : Exception, reified R> Result<V, F>
        .flatMap(f: (V) -> Result<R, F>): Result<R, F> =
    when (this) {
        is Result.Success -> f(value)
        is Result.Failure -> Result.Failure(failure)
    }

inline fun <reified V, reified F : Exception, reified R> Result<V, F>
        .map(f: (V) -> (R)): Result<R, F> =
    when (this) {
        is Result.Success -> Result.Success(f(value))
        is Result.Failure -> Result.Failure(failure)
    }

inline fun <reified V, reified F : Exception, reified R, reified C> Result<V, F>
        .zipWith(
    result: Result<R, F>,
    f: (V, R) -> (C)
): Result<C, F> =
    when (this) {
        is Result.Success -> when (result) {
            is Result.Success -> Result.Success(f(value, result.value))
            is Result.Failure -> Result.Failure(result.failure)
        }
        is Result.Failure -> Result.Failure(failure)
    }
