package com.example.core.ui.fragment

import androidx.databinding.ViewDataBinding

abstract class ComponentFragment<DB : ViewDataBinding> :
    BindingFragment<DB>() {
}