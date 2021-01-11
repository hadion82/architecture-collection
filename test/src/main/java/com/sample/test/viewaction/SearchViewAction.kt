package com.sample.test.viewaction

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class SearchViewAction(
    private val query: String
): ViewAction {

    companion object {
        fun typeSearchViewText(query: String): ViewAction =
            SearchViewAction(query)
    }

    override fun getDescription(): String {
        return "Change view text"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
    }

    override fun perform(uiController: UiController?, view: View?) {
        (view as SearchView).setQuery(query, true)
    }

}