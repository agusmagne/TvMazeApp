package com.example.tvmazeapp.viewmodel

import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tvmazeapp.R
import com.example.tvmazeapp.helperTest.ToastMatcher
import com.example.tvmazeapp.view.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private lateinit var context: Context

    @Before
    fun before() {
        activityScenarioRule.scenario.onActivity {
            decorView = it.window.decorView
            context = it
        }
    }


    @Test
    fun given_emptyInputString_then_messageShouldBeDisplayed() {
        val errorString = context.getString(R.string.ERROR_EMPTY_EDTXT)

        onView(withId(R.id.title_edtxt))
            .perform(ViewActions.pressImeActionButton())

        onView(withText(errorString))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))


    }


}
