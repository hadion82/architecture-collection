package com.example.architecture.collection.ui.user

import com.example.core.viewmodel.ViewModelDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

interface UserViewModelDelegate :
    ViewModelDelegate<UserViewIntent, UserViewState>

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
    override fun stateFlowOf(viewModelScope: CoroutineScope) =
        intentFlow.assemble()
            .map(intentProcessor::invoke)
            .flatMapMerge(transform = actionProcessor::invoke)
            .scan(idleState) { state, action -> action.reduce(state) }
            .catch { Timber.d("UserViewModelDelegate Throwable : $it") }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), idleState)

    override fun MutableSharedFlow<UserViewIntent>.assemble(): Flow<UserViewIntent> = this
}