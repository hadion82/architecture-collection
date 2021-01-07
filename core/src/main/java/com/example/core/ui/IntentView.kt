package com.example.core.ui

import com.example.core.presentation.ViewIntent
import com.example.core.presentation.ViewState
import kotlinx.coroutines.flow.Flow

interface IntentView<out VI : ViewIntent, in VS : ViewState> {
    val intents: Flow<VI>
    suspend fun render(state: VS)
}