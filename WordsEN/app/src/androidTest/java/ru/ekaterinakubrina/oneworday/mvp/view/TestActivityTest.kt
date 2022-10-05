package ru.ekaterinakubrina.oneworday.mvp.view

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
import ru.ekaterinakubrina.oneworday.R

@RunWith(AndroidJUnit4::class)
class TestActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), TestActivity::class.java).putExtra(
        "ID_USER",
        "I82PaPR9ZhYSYdR3KBreWfXKH8h2"
    ).putExtra(
        "LANGUAGE",
        0
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<TestActivity> =
        ActivityScenarioRule(intent)


    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.imageView5))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textView9))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textView31))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.buttonTheEnd))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        ViewMatchers.isClickable()
                    )
                )
            )
    }
}