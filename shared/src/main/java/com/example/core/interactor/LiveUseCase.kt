package com.example.core.interactor

import androidx.lifecycle.LiveData
import com.example.core.functional.FlowResult

abstract class LiveUseCase<in P, T> where T: Any {

    abstract fun execute(params: P): LiveData<FlowResult<T>>

    operator fun invoke(params: P) = execute(params)

}