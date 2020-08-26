package com.example.domain.core.net

import com.example.data.core.NetworkFailure
import com.example.core.functional.FlowResult

interface DomainHandler {

    fun handle(result: FlowResult.Failure<NetworkFailure>): FlowResult.Failure<NetworkFailure>
}