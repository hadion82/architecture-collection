package com.example.architecture.collection.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.core.viewmodel.ComponentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class UserViewModel @ViewModelInject constructor(
    userViewModelDelegate: UserViewModelDelegate,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ComponentViewModel(), UserViewModelDelegate by userViewModelDelegate {

    val viewState: StateFlow<UserViewState> = flowStateOf(viewModelScope)
}