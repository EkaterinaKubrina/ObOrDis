package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matchers

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.wordsen.R

@RunWith(AndroidJUnit4::class)
class SelectTestActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), SelectTestActivity::class.java).putExtra(
        "ID_USER",
        "1"
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<SelectTestActivity> =
        ActivityScenarioRule(intent)

    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.cardView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.cardView3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.startWeekTest))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isNotEnabled()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.startAllTest))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isNotEnabled()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.textView6))
            .check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
        Espresso.onView(ViewMatchers.withId(R.id.textView11))
            .check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }
}