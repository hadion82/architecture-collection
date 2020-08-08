package com.example.core.ui.activity

import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class ComponentActivity<VDB : ViewDataBinding> :
    BindingActivity<VDB>() {


    fun <T> LiveData<T>.collect(collect: (T) -> Unit) =
            this.observe(this@ComponentActivity, Observer { collect(it) })
}