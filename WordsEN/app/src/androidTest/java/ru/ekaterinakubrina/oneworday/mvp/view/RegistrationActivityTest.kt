package ru.ekaterinakubrina.oneworday.mvp.view

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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.oneworday.R

@RunWith(AndroidJUnit4::class)
class RegistrationActivityTest : TestCase() {
    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), RegistrationActivity::class.java)

    @get:Rule
    val activityTestRule: ActivityScenarioRule<RegistrationActivity> =
        ActivityScenarioRule(intent)


    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.imageView3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textPersonName))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.textEmailAddress))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.textPassword))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.textPassword2))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.buttonRegistration))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable(),
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.textError))
            .check(
                ViewAssertions.matches(
                    withText("")
                )
            )
    }
}