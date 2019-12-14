package com.example.article.articles.presentation.ui


import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.article.MainActivity
import com.example.article.R
import com.example.article.articles.presentation.ui.CustomAssertions.Companion.hasItemCount
import com.example.article.articles.presentation.ui.CustomMatchers.Companion.withItemCount
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ArticlesTest {


    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
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

    @Test
    fun checkIfNoDataIsAvailable() {
        if (getListCount() < 0) {
            onView(withId(R.id.llNoDataLayout)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testNetworkConnectivity() {
        assertTrue(isConnected(activityRule.activity))
    }

    private fun getListCount(): Int {
        val recyclerView =
            activityRule.activity.findViewById(R.id.articlesRecycler) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
    private fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}