package com.example.core.interactor

import androidx.lifecycle.LiveData
import com.example.core.functional.FlowResult

abstract class LiveUseCase<T, F, in P> where T: Any {

    abstract fun run(params: P): LiveData<FlowResult<T, F>>

    operator fun invoke(params: P) = run(params)

}