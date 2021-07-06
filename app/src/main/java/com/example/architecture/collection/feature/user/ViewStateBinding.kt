package com.example.architecture.collection.feature.user

import com.example.core.presentation.ViewState

interface ViewStateBinding<in T: ViewState> {

    suspend fun bind(state: T)

}