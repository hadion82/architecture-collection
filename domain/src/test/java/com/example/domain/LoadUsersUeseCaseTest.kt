package com.example.domain

import androidx.paging.PagingData
import com.example.architecture.data.DomainTestData
import com.example.core.functional.onFailure
import com.example.core.functional.onSuccess
import com.example.data.repository.UserDefaultRepository
import com.example.data.repository.UserRepository
import com.example.domain.feature.LoadUserByName
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sample.test.rule.MainCoroutineRule
import com.sample.test.rule.runBlockingTest
import com.sample.test.testdata.SharedTestData
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.SocketTimeoutException
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class LoadUsersUeseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val collectorRule = ErrorCollector()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadUsersByName_success() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(SharedTestData.testQuery, false) }
                .doReturn(
                    flow {
                        emit(
                            PagingData.from(
                                listOf(DomainTestData.testUser)
                            )
                        )
                    }
                )
        }
        val result = LoadUserByName(coroutineRule.testDispatcher, userRepository)(
            LoadUserByName.Params(SharedTestData.testQuery)
        )
        collectorRule.checkThat(result, notNullValue())
        result.onSuccess { flow ->
            collectorRule.checkThat(flow.first(), notNullValue())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadUsersByName_failure() = coroutineRule.runBlockingTest {
        val userRepository = mock<UserRepository> {
            onBlocking { loadUsers(SharedTestData.testQuery, false) }
                .thenThrow(SocketTimeoutException("Connection timeout.."))
        }
        val result = LoadUserByName(coroutineRule.testDispatcher, userRepository)(
            LoadUserByName.Params(SharedTestData.testQuery)
        )
        collectorRule.checkThat(result.isFailure, `is`(true))
        result.onFailure { exception ->
            collectorRule.checkThat(exception.message, `is`(equalTo("Connection timeout..")))
        }
    }

}