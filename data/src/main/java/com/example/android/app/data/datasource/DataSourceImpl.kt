package com.example.android.app.data.datasource

import android.net.Network
import com.example.android.app.core.NetworkFailure
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