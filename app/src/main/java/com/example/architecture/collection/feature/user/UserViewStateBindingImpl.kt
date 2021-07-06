package com.example.architecture.collection.feature.user

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface UserViewStateBinding: ViewStateBinding<UserViewState>

/**
 * Preview StateFlow-DataBinding
 */
class UserViewStateBindingImpl @Inject constructor(): UserViewStateBinding {

    val isLoadingFlow = MutableStateFlow(false)

    override suspend fun bind(state: UserViewState) {
        isLoadingFlow.emit(state.isLoading)
    }
}