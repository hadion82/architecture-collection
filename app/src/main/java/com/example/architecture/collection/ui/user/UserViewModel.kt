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

    fun queryChangedIntent(query: String): UserViewIntent.QueryChangedIntent {
        val currentTimeMillis = System.currentTimeMillis()
        val isRefresh: Boolean = currentTimeMillis -
                (savedStateHandle.get(query) ?: currentTimeMillis) > ONE_MIN
        savedStateHandle[query] = currentTimeMillis
        return UserViewIntent.QueryChangedIntent(
            query, isRefresh
        )
//        or
//        return UserViewIntent.QueryChangedIntent(query)
    }
}