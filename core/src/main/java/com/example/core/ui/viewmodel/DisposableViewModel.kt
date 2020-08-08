package com.example.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable?) {
        disposable?.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        disposeClear()
        super.onCleared()
    }

    fun Disposable.disposeOnCleared() {
        compositeDisposable.add(this)
    }

    fun disposeClear() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.clear()
    }
}