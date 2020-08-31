package com.example.data.core

sealed class Failure {

    object ConnectionError : Failure()

    data class ServerError(
        val status: Int,
        val errorCode: Int,
        val errorMessage: String?
    ) : Failure()

    data class Exception(
        val exception: java.lang.Exception
    ): Failure()
}