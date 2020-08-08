package com.example.demo

import android.os.Bundle
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.extensions.viewModel
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.Field
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<VDB : ViewDataBinding>: DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout())
        binding.lifecycleOwner = this
        binding.viewModel(this)
        onBind(savedInstanceState, binding)
    }

    open fun onBind(savedInstanceState: Bundle?, binding: VDB) {}

    abstract fun layout(): Int
}