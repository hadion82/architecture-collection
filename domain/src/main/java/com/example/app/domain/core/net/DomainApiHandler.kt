package com.example.app.domain.core.net

import com.example.android.app.core.NetworkFailure

object DomainApiHandler: DomainHandler {

    override fun handle(result: NetworkFailure): NetworkFailure {
        return result
    }
}