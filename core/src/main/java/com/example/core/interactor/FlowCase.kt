package com.example.core.interactor

import com.example.core.functional.FlowResult
import kotlinx.coroutines.flow.Flow

abstract class FlowCase<T, out F> where T: Any {

    abstract fun run(): Flow<FlowResult<T, F>>

    operator fun invoke() = run()

}