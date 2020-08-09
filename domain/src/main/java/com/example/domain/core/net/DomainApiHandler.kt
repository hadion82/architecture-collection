package com.example.domain.core.net

import com.example.data.core.NetworkFailure
import com.example.core.functional.Result

object DomainApiHandler: DomainHandler {

    override fun handle(result: Result.Failure<NetworkFailure>): Result.Failure<NetworkFailure> {

        return result
    }
}