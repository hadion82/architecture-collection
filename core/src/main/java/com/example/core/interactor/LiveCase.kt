package com.example.core.interactor

import androidx.lifecycle.LiveData

abstract class LiveCase<T> where T: Any {

    abstract fun run(): LiveData<T>

    operator fun invoke() = run()

}