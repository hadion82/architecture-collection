package com.example.architecture.collection.feature.user

import com.example.core.presentation.PresentationProcessor
import javax.inject.Inject

class UserIntentProcessor @Inject constructor(): PresentationProcessor<UserViewIntent, UserViewAction> {

    override suspend fun invoke(value: UserViewIntent): UserViewAction {
        return when (value) {
            is UserViewIntent.Initialize -> UserViewAction.InitializeAction
            is UserViewIntent.Refresh -> UserViewAction.QueryUsersAction(query = value.query, isRefresh = true)
            is UserViewIntent.QueryChangedIntent -> UserViewAction.QueryUsersAction(query = value.query, isRefresh = false)
            is UserViewIntent.OpenUserDetailIntent -> UserViewAction.OpenUserDetailAction(value.user)
        }
    }
}