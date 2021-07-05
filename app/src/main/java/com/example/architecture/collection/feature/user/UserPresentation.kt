package com.example.architecture.collection.feature.user

import androidx.paging.PagingData
import com.example.core.alias.Mapper
import com.example.core.functional.ViewEvent
import com.example.core.presentation.Reducer
import com.example.core.presentation.ViewIntent
import com.example.core.presentation.ViewState
import com.example.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

sealed class UserViewIntent: ViewIntent {
    object Initialize : UserViewIntent()
    data class Refresh(val query: String = ""): UserViewIntent()
    data class QueryChangedIntent(val query: String, var isRefresh: Boolean = false) : UserViewIntent()
    data class OpenUserDetailIntent(val user: UserEntity) : UserViewIntent()
}

sealed class UserViewAction {
    object InitializeAction : UserViewAction()
    object LoadUserAction : UserViewAction()
    data class QueryUsersAction(val query: String, val isRefresh: Boolean) : UserViewAction()
    data class OpenUserDetailAction(val user: UserEntity) : UserViewAction()
}

sealed class UserViewResult : Reducer<UserViewState> {
    object Initialize : UserViewResult() {
        override fun reduce(viewState: UserViewState): UserViewState =
            viewState.copy(
                isLoading = false,
                event = null
            )
    }
    object Loading : UserViewResult() {
        override fun reduce(viewState: UserViewState): UserViewState =
            viewState.copy(isLoading = true)
    }

    data class Open(val user: UserEntity) : UserViewResult() {
        override fun reduce(viewState: UserViewState): UserViewState =
            viewState.copy(
                isLoading = false,
                event = ViewEvent(UserViewEvent.OpenDetailInfo(user))
            )
    }

    sealed class Query: UserViewResult() {

        data class Data(val data: Flow<PagingData<UserEntity>>) : Query() {
            override fun reduce(viewState: UserViewState): UserViewState =
                viewState.copy(
                    isLoading = false,
                    event = ViewEvent(UserViewEvent.LoadPagingData(data))
                )
        }

        data class Error(val exception: Exception) : UserViewResult() {
            override fun reduce(viewState: UserViewState): UserViewState =
                viewState.copy(
                    isLoading = false,
                    event = ViewEvent(UserViewEvent.LoadFailed(exception))
                )
        }
    }
}

sealed class UserViewEvent {
    data class OpenDetailInfo(val user: UserEntity) : UserViewEvent()
    data class LoadPagingData(
        val data: Flow<PagingData<UserEntity>>
    ) : UserViewEvent()
    data class LoadFailed(val exception: Exception): UserViewEvent()
}

data class UserViewState constructor(
    val isLoading: Boolean,
    val event: ViewEvent<UserViewEvent>?
) : ViewState {
    companion object {
        fun idle() = UserViewState(
            isLoading = false,
            event = null
        )
    }
}

class UserLoadedMapper @Inject constructor() :
    Mapper<Flow<PagingData<UserEntity>>, UserViewResult.Query.Data> {
    override operator fun invoke(data: Flow<PagingData<UserEntity>>): UserViewResult.Query.Data =
        UserViewResult.Query.Data(data)
}

class UserLoadFailedMapper @Inject constructor() :
    Mapper<Exception, UserViewResult.Query.Error> {
    override operator fun invoke(exception: Exception): UserViewResult.Query.Error =
        UserViewResult.Query.Error(exception)
}

class UserLoadingMapper @Inject constructor() :
    Mapper<Unit, UserViewResult.Loading> {
    override operator fun invoke(unit: Unit): UserViewResult.Loading =
        UserViewResult.Loading
}
