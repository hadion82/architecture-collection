package com.example.core.interactor

import androidx.lifecycle.LiveData
import com.example.core.functional.Result

abstract class LiveUseCase<T, in P> where T: Any {

    abstract fun run(params: P): LiveData<T>

    operator fun invoke(params: P) = run(params)

}