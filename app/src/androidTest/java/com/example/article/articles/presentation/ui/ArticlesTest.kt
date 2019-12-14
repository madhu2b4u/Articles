package com.example.article.articles.presentation.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.article.MainActivity
import com.example.article.R
import com.example.article.articles.presentation.ui.CustomAssertions.Companion.hasItemCount
import com.example.article.articles.presentation.ui.CustomMatchers.Companion.withItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
public class ArticlesTest {


    @get:Rule
    public val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java,
        true,
        true
    )

    @Test
    fun countPrograms() {
        onView(withId(R.id.articlesRecycler)).check(matches(withItemCount(13)))
    }

    @Test
    fun countProgramsWithViewAssertion() {
        onView(withId(R.id.articlesRecycler)).check(hasItemCount(13))
    }

    @Test
    fun testToSwipeUpToCheckCompleteDataLoaded() {
        onView(withId(R.id.articlesRecycler)).perform(ViewActions.swipeUp())
    }

}