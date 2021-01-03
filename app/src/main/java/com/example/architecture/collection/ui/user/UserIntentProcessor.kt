package com.example.architecture.collection.ui.user

import com.example.core.presentation.PresentationProcessor

class UserIntentProcessor : PresentationProcessor<UserViewIntent, UserViewAction> {

    override suspend fun to(value: UserViewIntent): UserViewAction =
        when (value) {
            is UserViewIntent.Initialize -> UserViewAction.LoadUserAction
            is UserViewIntent.QueryChangedIntent -> UserViewAction.QueryUsersAction(value.query)
            is UserViewIntent.OpenUserDetailIntent -> UserViewAction.OpenUserDetailAction(value.id)
        }
}