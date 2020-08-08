package com.example.core.ui.viewmodel

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.kiwiplus.app.core.ui.listener.Throttle
import timber.log.Timber
import java.util.concurrent.TimeUnit

abstract class BindingViewModel : DisposableViewModel(), Throttle.ClickListener {

    override fun throttleClick(view: View, onClick: () -> Unit, skipDuration: Long) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnCleared()
}