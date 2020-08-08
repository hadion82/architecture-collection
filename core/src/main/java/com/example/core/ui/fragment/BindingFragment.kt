package com.example.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.view.RxView
import io.kiwiplus.app.core.extensions.viewModel
import io.kiwiplus.app.core.ui.listener.Throttle
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BindingFragment<DB : ViewDataBinding>: DisposableFragment(), Throttle.ClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding : DB

    private var created: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel(this, factory)
        onBinding(binding, created)
        created = false
    }

    override fun throttleClick(view: View, onClick: () -> Unit, skipDuration: Long) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnDestroy()

    open fun onBinding(binding : DB, created: Boolean) {

    }

    abstract fun layout() : Int
}