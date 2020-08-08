package com.example.app.domain.core.push.response

import com.google.gson.annotations.SerializedName

abstract class PushResponse {

    @SerializedName("TYPE")
    lateinit var type: String

    @SerializedName("SEND_TIME")
    var sendTime: Long = 0
}