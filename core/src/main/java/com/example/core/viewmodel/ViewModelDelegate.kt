package com.example.core.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewModelDelegate<ViewIntent, ViewState> {
    val intentFlow: MutableSharedFlow<ViewIntent>

    val idleState: ViewState

    suspend fun processIntents(intent: ViewIntent)

    fun stateFlowOf(viewModelScope: CoroutineScope): StateFlow<ViewState>

    fun MutableSharedFlow<ViewIntent>.implement(): Flow<ViewIntent>
}