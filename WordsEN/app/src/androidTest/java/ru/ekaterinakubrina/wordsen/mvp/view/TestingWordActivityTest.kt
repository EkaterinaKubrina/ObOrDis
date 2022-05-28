package ru.ekaterinakubrina.wordsen.mvp.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matchers

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.wordsen.R

@RunWith(AndroidJUnit4::class)
class TestingWordActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), TestingWordActivity::class.java).putExtra(
        "ID_USER",
        "I82PaPR9ZhYSYdR3KBreWfXKH8h2"
    ).putExtra(
        "TEST_TYPE",
        "all"
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<TestingWordActivity> =
        ActivityScenarioRule(intent)


    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.testWord))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.radioGroup))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.toAnswer))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isEnabled(),
                        ViewMatchers.isClickable(),
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.indexCurrentWord))
            .check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }
}