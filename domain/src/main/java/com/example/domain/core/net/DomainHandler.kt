package com.example.domain.core.net

import com.example.data.core.Failure
import com.example.core.functional.FlowResult

interface DomainHandler {

    fun handle(result: FlowResult.Failure<Failure>): FlowResult.Failure<Failure>
}