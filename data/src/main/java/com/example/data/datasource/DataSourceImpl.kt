package com.example.data.datasource

import com.example.data.core.NetworkFailure
import com.example.core.functional.Result

abstract class DataSourceImpl {

    inline fun <T> catchResult(block: () -> T): Result<T, Exception> =
        try {
            Result.Success(block())
        } catch (e: Exception) {
            Result.Failure(e)
        }

    inline fun <T> catchNetworkResult(block: () -> T): Result<T, NetworkFailure> =
        try {
            Result.Success(block())
        } catch (e: Exception) {
            Result.Failure(NetworkFailure.NetworkException(e))
        }
}