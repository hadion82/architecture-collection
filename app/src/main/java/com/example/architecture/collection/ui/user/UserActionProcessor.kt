package com.example.architecture.collection.ui.user

import com.example.core.functional.subscribe
import com.example.core.presentation.PresentationProcessor
import com.example.domain.feature.LoadUserByName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserActionProcessor @Inject constructor(
    private val loadUserByName: LoadUserByName,
    private val UserLoadedMapper: UserLoadedMapper,
    private val userLoadFailedMapper: UserLoadFailedMapper
) : PresentationProcessor<UserViewAction, Flow<UserViewResult>> {

    override suspend fun invoke(value: UserViewAction): Flow<UserViewResult> {
        return when (value) {
            is UserViewAction.InitializeAction -> initializeAction()
            is UserViewAction.LoadUserAction -> loadingAction()
            is UserViewAction.QueryUsersAction -> loadUsersAction(value.query)
            is UserViewAction.OpenUserDetailAction -> openDetailInfoAction(value.id)
        }
    }


    private fun initializeAction() = flowOf(UserViewResult.Initialize)

    private fun loadingAction() = flowOf(UserViewResult.Loading)

    private suspend fun loadUsersAction(query: String) =
        flowOf(loadUserByName(LoadUserByName.Params(query)))
            .map { it.subscribe(UserLoadedMapper, userLoadFailedMapper) }

    private fun openDetailInfoAction(id: Long) = flowOf(UserViewResult.Open(id))
}