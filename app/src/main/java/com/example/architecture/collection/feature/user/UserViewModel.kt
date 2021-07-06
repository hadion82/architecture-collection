package com.example.architecture.collection.feature.user

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
    private val savedStateHandle: SavedStateHandle,
    private val userViewStateBinding: UserViewStateBinding
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    companion object {
        const val LAST_QUERY = "LAST_QUERY"
    }

    val viewState: Flow<UserViewState> = stateFlowOf(viewModelScope)
        .onEach { userViewStateBinding.bind(it) }

    override fun MutableSharedFlow<UserViewIntent>.assemble(): Flow<UserViewIntent> {
        val initialIntent = filterIsInstance<UserViewIntent.Initialize>().take(1)
        val queryIntent = filterIsInstance<UserViewIntent.QueryChangedIntent>()
            .map { intent ->
                savedStateHandle[LAST_QUERY] = intent.query
                intent
            }
        val detailIntent = filterIsInstance<UserViewIntent.OpenUserDetailIntent>()
        val refreshIntent = filterIsInstance<UserViewIntent.Refresh>()
            .map { intent ->
                intent.copy(query = savedStateHandle[LAST_QUERY] ?: "")
            }
        return merge(
            initialIntent, refreshIntent, queryIntent, detailIntent
        )
    }
}