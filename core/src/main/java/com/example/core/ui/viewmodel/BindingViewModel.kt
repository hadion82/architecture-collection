package com.example.core.ui.viewmodel

import android.view.View
import com.example.core.ui.listener.Throttle
import com.jakewharton.rxbinding2.view.RxView
import timber.log.Timber
import java.util.concurrent.TimeUnit

abstract class BindingViewModel : DisposableViewModel(), Throttle.ClickListener {

    override fun throttleClick(view: View, skipDuration: Long, onClick: () -> Unit) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnCleared()
}