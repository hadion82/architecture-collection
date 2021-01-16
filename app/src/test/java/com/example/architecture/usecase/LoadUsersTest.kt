package com.example.architecture.usecase

import com.example.core.functional.onSuccess
import com.example.data.repository.UserDefaultRepository
import com.example.data.repository.UserRepository
import com.example.domain.feature.LoadUserByName
import com.sample.test.rule.MainCoroutineRule
import com.sample.test.rule.runBlockingTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class LoadUsersTest {

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
    fun loadUsersByNameTest() = coroutineRule.runBlockingTest {
        val result = LoadUserByName(coroutineRule.testDispatcher, userRepository)(
            LoadUserByName.Params("hq")
        )
        collectorRule.checkThat(result, notNullValue())
        result.onSuccess { flow ->
            collectorRule.checkThat(flow, notNullValue())
            collectorRule.checkThat(flow.first(), notNullValue())
        }
    }
}