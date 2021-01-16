package com.example.architecture.collection.ui.user

import com.example.core.presentation.PresentationProcessor
import javax.inject.Inject

class UserIntentProcessor @Inject constructor(): PresentationProcessor<UserViewIntent, UserViewAction> {

    override suspend fun invoke(value: UserViewIntent): UserViewAction {
        return when (value) {
            is UserViewIntent.Initialize -> UserViewAction.InitializeAction
            is UserViewIntent.QueryChangedIntent -> UserViewAction.QueryUsersAction(value.query, value.isRefresh)
            is UserViewIntent.OpenUserDetailIntent -> UserViewAction.OpenUserDetailAction(value.user)
        }
    }
}