package com.example.architecture.collection.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.core.viewmodel.ComponentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
class UserViewModel @ViewModelInject constructor(
    private val intentProcessor: UserIntentProcessor,
    private val actionProcessor: UserActionProcessor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ComponentViewModel() {

    private val _intentFlow = MutableSharedFlow<UserViewIntent>(extraBufferCapacity = 32)

    val viewState: StateFlow<UserViewState>

    private val idleState = UserViewState.idle()

    suspend fun processIntents(intent: UserViewIntent) =
        _intentFlow.emit(intent)

    init {
        viewState = merge(
            _intentFlow.filterIsInstance<UserViewIntent.Initialize>().take(1),
            _intentFlow.filterNot { it is UserViewIntent.Initialize }
        )
            .map(intentProcessor::to)
            .flatMapMerge(transform = actionProcessor::to)
            .scan(idleState) { state, action -> action.reduce(state) }
            .catch { Timber.d("UserViewModel Throwable : $it") }
            .stateIn(viewModelScope, SharingStarted.Eagerly, idleState)
    }

    companion object {
        const val QUERY = "query"
    }
}