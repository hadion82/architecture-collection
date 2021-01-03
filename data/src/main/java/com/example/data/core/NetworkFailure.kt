package com.example.data.core

sealed class NetworkFailure {

    object ConnectionError : NetworkFailure()

    data class Exception(
        val exception: java.lang.Exception
    ): NetworkFailure()
}