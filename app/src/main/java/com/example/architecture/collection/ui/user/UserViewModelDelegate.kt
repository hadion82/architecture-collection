package com.example.architecture.collection.ui.user

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

interface UserViewModelDelegate {

    val intentFlow: MutableSharedFlow<UserViewIntent>

    val idleState: UserViewState

    suspend fun processIntents(intent: UserViewIntent)

    fun flowStateOf(viewModelScope: CoroutineScope): StateFlow<UserViewState>

}

internal class UserViewModelDelegateImpl @Inject constructor(
    private val intentProcessor: UserIntentProcessor,
    private val actionProcessor: UserActionProcessor
) : UserViewModelDelegate {

    override val intentFlow: MutableSharedFlow<UserViewIntent> =
        MutableSharedFlow(extraBufferCapacity = 32)

    override val idleState: UserViewState = UserViewState.idle()

    override suspend fun processIntents(intent: UserViewIntent) =
        intentFlow.emit(intent)

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun flowStateOf(viewModelScope: CoroutineScope) = merge(
        intentFlow.filterIsInstance<UserViewIntent.Initialize>().take(1),
        intentFlow.filterNot { it is UserViewIntent.Initialize }
    )
        .map(intentProcessor::invoke)
        .flatMapMerge(transform = actionProcessor::invoke)
        .scan(idleState) { state, action -> action.reduce(state) }
        .catch { Timber.d("UserViewModelDelegate Throwable : $it") }
        .stateIn(viewModelScope, SharingStarted.Eagerly, idleState)
}