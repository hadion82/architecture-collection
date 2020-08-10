package com.example.data.core

sealed class NetworkFailure: Exception {

    constructor(): super()

    constructor(e: Exception): super(e)

    object ConnectionError : NetworkFailure()

    data class ServerError(
        val status: Int,
        val errorCode: Int,
        val errorMessage: String?
    ) : NetworkFailure()

    data class NetworkException(
        val exception: Exception
    ): NetworkFailure(exception)
}