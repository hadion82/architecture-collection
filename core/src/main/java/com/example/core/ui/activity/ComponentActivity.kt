package com.example.core.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.window.DeviceState
import androidx.window.WindowBackend
import androidx.window.WindowManager
import com.example.core.ui.window.WindowDeviceState
import timber.log.Timber

abstract class ComponentActivity<VDB : ViewDataBinding> :
    BindingActivity<VDB>() {

    private var windowState: WindowDeviceState? = null

    fun <T> LiveData<T>.collect(collect: (T) -> Unit) =
            this.observe(this@ComponentActivity, Observer { collect(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowState = WindowDeviceState(this, null)
        windowState?.registerDeviceStateChange(
            lifecycleScope,
            this::onDeviceStateChange
        )
    }

    override fun onDestroy() {
        windowState?.unregisterDeviceStateChange()
        super.onDestroy()
    }

    open fun onDeviceStateChange(deviceState: DeviceState) {

    }
}