package ru.ekaterinakubrina.oneworday.mvp.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.oneworday.R

@RunWith(AndroidJUnit4::class)
class SelectLanguageActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), SelectLanguageActivity::class.java).putExtra(
        "ID_USER",
        "1"
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<ResultTestActivity> =
        ActivityScenarioRule(intent)


    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(withId(R.id.imageView10))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.buttonNext))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(withId(R.id.spinner))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                    )
                )
            )
        Espresso.onView(withId(R.id.spinner)).perform(click())
    }
}