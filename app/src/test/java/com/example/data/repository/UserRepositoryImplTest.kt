package com.example.data.repository

import com.example.AndroidHiltTest
import com.example.core.functional.onSuccess
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class UserRepositoryImplTest: AndroidHiltTest() {

    @Inject
    lateinit var repository: UserRepositoryImpl

    @Before
    fun init() {
        hiltRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testInjectRepository() = runBlocking(Dispatchers.IO) {
        loadUsers()
    }

    private suspend fun loadUsers() = withContext(Dispatchers.IO) {
        repository.loadUsers("hq").collectLatest {
            assert(it.isLoading || it.isSuccess)
        }
    }
}