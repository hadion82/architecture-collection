package com.example.core.interactor

import androidx.lifecycle.LiveData
import com.example.core.functional.Result

abstract class LiveUseCase<Type, in Params> where Type: Any {

    abstract fun run(params: Params): LiveData<Result<Type, Exception>>

    operator fun invoke(params: Params) = run(params)
}