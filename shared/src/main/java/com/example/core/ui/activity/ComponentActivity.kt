package com.example.core.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.window.FoldingFeature
import androidx.window.FoldingFeature.Companion.STATE_FLAT
import androidx.window.FoldingFeature.Companion.STATE_HALF_OPENED
import androidx.window.WindowLayoutInfo
import com.example.core.ui.window.WindowLayoutState

abstract class ComponentActivity<VDB : ViewDataBinding> :
    BindingActivity<VDB>() {

    private var windowState: WindowLayoutState? = null

    fun <T> LiveData<T>.collect(collect: (T) -> Unit) =
            this.observe(this@ComponentActivity, Observer { collect(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowState = WindowLayoutState(this)
        windowState?.registerLayoutChange(
            lifecycleScope,
            this::onLayoutStateChange
        )
    }

    override fun onDestroy() {
        windowState?.unregisterLayoutChange()
        super.onDestroy()
    }

    private fun onLayoutStateChange(layoutInfo: WindowLayoutInfo) {
        layoutInfo.displayFeatures.forEach { feature ->
            if(feature is FoldingFeature) {
                when(feature.state) {
                    STATE_HALF_OPENED -> onStateHalfOpened()
                    STATE_FLAT -> onStateFlat()
                }
            }
        }
    }

    open fun onStateHalfOpened() {

    }

    open fun onStateFlat() {

    }
}