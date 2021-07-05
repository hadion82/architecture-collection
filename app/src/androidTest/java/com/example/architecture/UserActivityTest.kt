package com.example.architecture

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.architecture.collection.feature.user.UserActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(UserActivity::class.java)

    @ExperimentalCoroutinesApi
    @Test
    fun testUserActivity() {
        onView(withId(R.id.search)).check(matches(isDisplayed()))
    }
}