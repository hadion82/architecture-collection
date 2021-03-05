package com.example.architecture.usecase

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.architecture.collection.ui.user.*
import com.example.architecture.testdata.TestData
import com.example.data.repository.UserRepository
import com.example.domain.feature.LoadUserByName
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sample.test.rule.MainCoroutineRule
import com.sample.test.rule.runBlockingTest
import com.sample.test.utils.CoroutineTestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

class UserMoveModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun init() {

    }

    @InternalCoroutinesApi
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun loadUser_success() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(TestData.testQuery, false) }
                .doReturn(
                    flow {
                        emit(
                            PagingData.from(
                                listOf(TestData.testUser)
                            )
                        )
                    }
                )
        }
        val userNameUseCase = LoadUserByName(coroutineRule.testDispatcher, userRepository)
        val userViewModelDelegate = FakeUserViewModelDelegate(
            UserIntentProcessor(), UserActionProcessor(
                userNameUseCase, UserLoadedMapper(), UserLoadFailedMapper()
            )
        )

        val userViewModel = UserViewModel(userViewModelDelegate, SavedStateHandle())
        userViewModel.processIntents(UserViewIntent.QueryChangedIntent(TestData.testQuery))
        runBlockingTest {
            val initState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(initState?.isLoading == false)
            val loadState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(loadState?.isLoading == false)
            assert(loadState?.event?.getEvent() is UserViewEvent.LoadPagingData)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun loadUser_failed() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(TestData.testQuery, false) }
                .thenThrow(SocketTimeoutException())
        }
        val userNameUseCase = LoadUserByName(coroutineRule.testDispatcher, userRepository)
        val userViewModelDelegate = FakeUserViewModelDelegate(
            UserIntentProcessor(), UserActionProcessor(
                userNameUseCase, UserLoadedMapper(), UserLoadFailedMapper()
            )
        )
        val userViewModel = UserViewModel(userViewModelDelegate, SavedStateHandle())
        userViewModel.processIntents(UserViewIntent.QueryChangedIntent(TestData.testQuery))
        runBlockingTest {
            val initState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(initState?.isLoading == false)
            val loadState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(loadState?.isLoading == false)
            assert(loadState?.event?.getEvent() is UserViewEvent.LoadFailed)
        }
    }
}