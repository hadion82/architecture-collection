package com.example.core.ui.fragment

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableFragment : Fragment() {

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