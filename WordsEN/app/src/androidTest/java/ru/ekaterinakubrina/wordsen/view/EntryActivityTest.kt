package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
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
class EntryActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), EntryActivity::class.java)

    @get:Rule
    val activityTestRule: ActivityScenarioRule<EntryActivity> =
        ActivityScenarioRule(intent)

    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.TextEmail))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.TextPassword))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.buttonEntry))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.buttonEntry))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        ViewMatchers.isEnabled()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)
                )
            )
    }
}