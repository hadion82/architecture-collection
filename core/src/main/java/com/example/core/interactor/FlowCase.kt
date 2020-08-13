package com.example.core.interactor

import kotlinx.coroutines.flow.Flow

abstract class FlowCase<T> where T: Any {

    abstract fun run(): Flow<T>

    operator fun invoke() = run()

}