package com.example.app.domain.core.net

import com.example.android.app.core.NetworkFailure

interface DomainHandler {

    fun handle(result: NetworkFailure): NetworkFailure
}