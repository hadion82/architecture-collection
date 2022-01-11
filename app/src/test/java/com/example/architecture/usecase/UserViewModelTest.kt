package com.example.architecture.usecase

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.architecture.collection.feature.user.*
import com.example.architecture.data.TestData
import com.example.data.repository.UserRepository
import com.example.domain.feature.LoadUserByName
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sample.test.rule.MainCoroutineRule
import com.sample.test.rule.runBlockingTest
import com.sample.test.testdata.SharedTestData
import com.sample.test.utils.CoroutineTestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

class UserViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun init() {

    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
    @Test
    fun loadUser_success() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(SharedTestData.testQuery, false) }
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
        val userViewModel = createViewModel(coroutineRule.testDispatcher, userRepository, SavedStateHandle())
        userViewModel.processIntents(UserViewIntent.QueryChangedIntent(SharedTestData.testQuery))
        runBlockingTest {
            val initState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(initState?.isLoading == false)
            val loadState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(loadState?.isLoading == false)
            assert(loadState?.event?.getEvent() is UserViewEvent.LoadPagingData)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    @Test
    fun loadUser_failed() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(SharedTestData.testQuery, false) }
                .thenThrow(SocketTimeoutException())
        }
        val userViewModel = createViewModel(coroutineRule.testDispatcher, userRepository, SavedStateHandle())
        userViewModel.processIntents(UserViewIntent.QueryChangedIntent(SharedTestData.testQuery))
        runBlockingTest {
            val initState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(initState?.isLoading == false)
            val loadState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(loadState?.isLoading == false)
            assert(loadState?.event?.getEvent() is UserViewEvent.LoadFailed)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    @Test
    fun userDetailTest() = coroutineRule.runBlockingTest {
        val userViewModel = createViewModel(coroutineRule.testDispatcher, mock(), SavedStateHandle())
        userViewModel.processIntents(UserViewIntent.OpenUserDetailIntent(TestData.testUser))
        runBlockingTest {
            val initState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(initState?.isLoading == false)
            val loadState = CoroutineTestUtil.getValue(userViewModel.viewState, this)
            assert(loadState?.isLoading == false)
            assert(loadState?.event?.getEvent() is UserViewEvent.OpenDetailInfo)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun createViewModel(
        dispatcher: TestCoroutineDispatcher,
        userRepository: UserRepository,
        savedStateHandle: SavedStateHandle
    ): UserViewModel {
        val userNameUseCase = LoadUserByName(dispatcher, userRepository)
        val userViewModelDelegate = UserViewModelDelegateImpl(
            UserIntentProcessor(), UserActionProcessor(
                userNameUseCase, UserLoadedMapper(), UserLoadFailedMapper()
            )
        )
        return UserViewModel(userViewModelDelegate, savedStateHandle)
    }
}