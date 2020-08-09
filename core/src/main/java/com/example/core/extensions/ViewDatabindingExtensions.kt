package com.example.core.extensions

import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun ViewDataBinding.viewModel(owner: ViewModelStoreOwner) {
    var targetClass: Class<*>? = this::class.java
    while (targetClass != null) {
        targetClass.declaredFields.filter {
            it.getAnnotation(Bindable::class.java) != null &&
                    ViewModel::class.java.isAssignableFrom(it.type)
        }.forEach {
            it.isAccessible = true
            it.set(this, ViewModelProvider(owner)
                .get(it.type as Class<out ViewModel>))
        }
        targetClass = targetClass.superclass
    }
}

fun ViewDataBinding.viewModel(owner: ViewModelStoreOwner, factory: ViewModelProvider.Factory) {
    var targetClass: Class<*>? = this::class.java
    while (targetClass != null) {
        targetClass.declaredFields.filter {
            it.getAnnotation(Bindable::class.java) != null &&
                    ViewModel::class.java.isAssignableFrom(it.type)
        }.forEach {
            it.isAccessible = true
            it.set(this, ViewModelProvider(owner)
                    .get(it.type as Class<out ViewModel>))
        }
        targetClass = targetClass.superclass
    }
}