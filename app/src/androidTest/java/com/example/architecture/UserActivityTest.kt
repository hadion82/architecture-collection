package com.example.architecture

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.architecture.collection.ui.user.UserActivity
import com.sample.test.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.*

@RunWith(AndroidJUnit4::class)
class UserActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(UserActivity::class.java)

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @Test
    fun testUserActivity() {
        with(activityRule.scenario) {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.search)).check(matches(withHint("Enter the query")))
        }
    }

    /*@ExperimentalCoroutinesApi
    private suspend fun waitForAdapterChange(recyclerView: RecyclerView?) =  suspendCancellableCoroutine<Unit> { cont ->
        val listener = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {}

            override fun onChanged() {
                cont.resume(Unit) {}
            }
        }

        cont.invokeOnCancellation { recyclerView?.adapter?.unregisterAdapterDataObserver(listener) }
        recyclerView?.adapter?.registerAdapterDataObserver(listener)

    }*/
}