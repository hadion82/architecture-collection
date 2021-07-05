package com.example.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.core.extensions.viewModel
import com.example.core.ui.listener.Throttle
import com.jakewharton.rxbinding2.view.RxView
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BindingFragment<DB : ViewDataBinding>: DisposableFragment(), Throttle.ClickListener {

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
        onBind(binding, created)
        created = false
    }

    override fun throttleClick(view: View, skipDuration: Long, onClick: () -> Unit) =
        RxView.clicks(view).throttleFirst(skipDuration, TimeUnit.MILLISECONDS)
            .subscribe({ onClick() }
                , { error -> Timber.d(error) })
            .disposeOnDestroy()

    open fun onBind(binding : DB, created: Boolean) {

    }

    abstract fun layout() : Int
}