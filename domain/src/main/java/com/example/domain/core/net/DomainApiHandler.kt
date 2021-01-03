package com.example.domain.core.net

import com.example.data.core.NetworkFailure
import com.example.core.functional.FlowResult

object DomainApiHandler: DomainHandler {

    override fun handle(result: FlowResult.Failure<NetworkFailure>): FlowResult.Failure<NetworkFailure> {

        return result
    }
}