package com.example.architecture.usecase

import androidx.paging.PagingData
import com.example.core.functional.FlowResult
import com.example.core.functional.subscribe
import com.example.data.entity.UserEntity
import com.example.data.repository.UserDefaultRepository
import com.example.data.repository.UserRepository
import com.example.domain.feature.LoadUserByName
import com.sample.test.rule.MainCoroutineRule
import com.sample.test.rule.runBlockingTest
import com.sample.test.testdata.SharedTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class LoadUsersNetworkTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val collectorRule = ErrorCollector()

    @UserDefaultRepository
    @Inject
    lateinit var userRepository: UserRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadUsersByName_with_network() = coroutineRule.runBlockingTest {
        val result: FlowResult<Flow<PagingData<UserEntity>>> =
            LoadUserByName(coroutineRule.testDispatcher, userRepository)(
                LoadUserByName.Params(SharedTestData.testQuery)
            )
        collectorRule.checkThat(result, notNullValue())
        result.subscribe({ flow ->
            collectorRule.checkThat(flow.first(), notNullValue())
        }, { exception ->
            collectorRule.checkThat(exception, notNullValue())
        })
    }
}