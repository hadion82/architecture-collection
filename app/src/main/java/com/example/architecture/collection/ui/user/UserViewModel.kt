package com.example.architecture.collection.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class UserViewModel @Inject constructor(
    userViewModelDelegate: UserViewModelDelegate,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    companion object {
        const val ONE_MIN = 60 * 1000
    }

    val viewState: StateFlow<UserViewState> = stateFlowOf(viewModelScope)

    override fun MutableSharedFlow<UserViewIntent>.assemble(): Flow<UserViewIntent> {
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