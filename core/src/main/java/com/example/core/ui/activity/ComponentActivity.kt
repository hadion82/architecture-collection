package com.example.core.ui.activity

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class ComponentActivity<VDB : ViewDataBinding> :
    BindingActivity<VDB>() {

    fun <T> LiveData<T>.collect(collect: (T) -> Unit) =
            this.observe(this@ComponentActivity, Observer { collect(it) })
}