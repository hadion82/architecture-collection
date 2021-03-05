package com.example.architecture.usecase

import com.example.architecture.collection.ui.user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber

class FakeUserViewModelDelegate(
    private val intentProcessor: UserIntentProcessor,
    private val actionProcessor: UserActionProcessor
): UserViewModelDelegate {

    override val intentFlow: MutableSharedFlow<UserViewIntent>
    = MutableSharedFlow(extraBufferCapacity = 32)

    override val idleState: UserViewState
        get() = UserViewState.idle()

    override suspend fun processIntents(intent: UserViewIntent) =
        intentFlow.emit(intent)

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun stateFlowOf(viewModelScope: CoroutineScope): StateFlow<UserViewState> =
        intentFlow.assemble()
            .map(intentProcessor::invoke)
            .flatMapMerge(transform = actionProcessor::invoke)
            .scan(idleState) { state, action -> action.reduce(state) }
            .catch { Timber.d("UserViewModelDelegate Throwable : $it") }
            .stateIn(viewModelScope, SharingStarted.Eagerly, idleState)

    override fun MutableSharedFlow<UserViewIntent>.assemble(): Flow<UserViewIntent> = this
}