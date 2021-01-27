package com.example.architecture.collection.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class UserViewModel @ViewModelInject constructor(
    userViewModelDelegate: UserViewModelDelegate,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    companion object {
        const val ONE_MIN = 60 * 1000
    }

    val viewState: StateFlow<UserViewState> = stateFlowOf(viewModelScope)

    override fun MutableSharedFlow<UserViewIntent>.implement(): Flow<UserViewIntent> {
        val initialIntent = filterIsInstance<UserViewIntent.Initialize>().take(1)
        val queryIntent = filterIsInstance<UserViewIntent.QueryChangedIntent>()
            .map { intent ->
                val currentTimeMillis = System.currentTimeMillis()
                val isRefresh: Boolean = currentTimeMillis -
                        (savedStateHandle.get(intent.query) ?: currentTimeMillis) > ONE_MIN
                savedStateHandle[intent.query] = currentTimeMillis
                intent.apply { this.isRefresh = isRefresh }
            }
        val detailIntent = filterIsInstance<UserViewIntent.OpenUserDetailIntent>()
        return merge(
            initialIntent, queryIntent, detailIntent
        )
    }
}