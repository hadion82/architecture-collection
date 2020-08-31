package com.example.domain.core.net

import com.example.data.core.Failure
import com.example.core.functional.FlowResult

object DomainApiHandler: DomainHandler {

    override fun handle(result: FlowResult.Failure<Failure>): FlowResult.Failure<Failure> {

        return result
    }
}