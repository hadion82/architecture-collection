package com.example.core.functional

sealed class Result<out T, out F> {

    data class Success<out T>(val result: T) : Result<T, Nothing>()
    data class Failure<out F>(val failure: F) : Result<Nothing, F>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure<F>

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$result]"
            is Failure -> "Error[exception=$failure]"
        }
    }

    fun dispatch(onResult: (T) -> Any, onFailure: (F) -> Any): Any =
            when (this) {
                is Success -> onResult(result)
                is Failure -> onFailure(failure)
            }
}