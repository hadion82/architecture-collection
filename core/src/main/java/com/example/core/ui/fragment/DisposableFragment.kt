package com.example.core.ui.fragment

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableFragment : DaggerFragment() {

    private val compositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable?) {
        disposable?.let { compositeDisposable.add(it) }
    }


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun Disposable.disposeOnDestroy() {
        compositeDisposable.add(this)
    }
}