package com.example.core.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.extensions.viewModel
import com.example.core.ui.listener.Throttle
import com.jakewharton.rxbinding2.view.RxView
import timber.log.Timber
import java.util.concurrent.TimeUnit

abstract class BindingActivity<DB : ViewDataBinding> :
    DisposableActivity(), LifecycleOwner, Throttle.ClickListener {

    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout())
        onBind(savedInstanceState, binding)
    }

    override fun throttleClick(view: View, skipDuration: Long, onClick: () -> Unit) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnDestroy()

    abstract fun layout(): Int

    open fun onBind(savedInstanceState: Bundle?, binding: DB) {}
}