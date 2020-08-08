package com.example.core.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.core.extensions.viewModel
import com.example.core.ui.listener.Throttle
import com.jakewharton.rxbinding2.view.RxView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
abstract class BindingActivity<DB : ViewDataBinding> :
    DisposableActivity(), LifecycleOwner, Throttle.ClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel(this, factory)
        onBind(binding)
    }

    override fun throttleClick(view: View, onClick: () -> Unit, skipDuration: Long) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnDestroy()

    abstract fun layout(): Int

    open fun onBind(binding: DB) {

    }
}