package com.example.domain.core.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core.ui.viewmodel.BindingViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class ComponentViewModel : BindingViewModel() {

    fun launch(dispatchers: CoroutineDispatcher =
                       Dispatchers.Default, launch: suspend () -> Unit) =
            viewModelScope.launch(dispatchers) { launch() }

    fun <T> async(dispatchers: CoroutineDispatcher =
                          Dispatchers.Default, launch: suspend () -> T) =
            viewModelScope.async(dispatchers) { launch() }
}