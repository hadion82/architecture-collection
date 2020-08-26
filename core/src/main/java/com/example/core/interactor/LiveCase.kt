package com.example.core.interactor

import androidx.lifecycle.LiveData
import com.example.core.functional.FlowResult

abstract class LiveCase<T, F> where T: Any {

    abstract fun run(): LiveData<FlowResult<T?, F>>

    operator fun invoke(): LiveData<FlowResult<T?, F>> = run()

}