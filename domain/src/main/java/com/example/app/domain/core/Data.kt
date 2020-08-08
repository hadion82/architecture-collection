package com.example.app.domain.core

import com.google.gson.annotations.SerializedName


class Data<T> {

    @SerializedName("status")
    var status : Int = 0

    @SerializedName("errorCode")
    var errorCode : Int = 0

    @SerializedName("errorMessage")
    var errorMessage : String? = null

    var data : T? = null

    /*sealed class NetworkResult<out R> {

        object ConnectionError
        data class Success<out T>(val data: T) : NetworkResult<T>()
        data class HttpException(val exception: Exception) : NetworkResult<Nothing>()
        data class ServerError(val status: Int, val errorCode: Int, val errorMessage: String?) : NetworkResult<Nothing>()

        object Loading : NetworkResult<Nothing>()

        override fun toString(): String {
            return when (this) {
                is Success<*> -> "Success[data=$data]"
                is HttpException -> "Error[exception=$exception]"
                is Loading -> "Loading"
                is ServerError -> "ServiceError[errorCode=${errorCode} ,error=${errorMessage}]"
            }
        }
    }*/
}