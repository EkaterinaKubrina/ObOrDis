package ru.ekaterinakubrina.wordsen.mvp.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matchers

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.wordsen.R

@RunWith(AndroidJUnit4::class)
class DictionaryActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), DictionaryActivity::class.java).putExtra(
            "ID_USER",
            "1"
        )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<DictionaryActivity> =
        ActivityScenarioRule(intent)

    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(withId(R.id.scroll1))
            .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.buttonAddMyWord))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        isDisplayed(),
                        isClickable()
                    )
                )
            )
        Espresso.onView(withId(R.id.imageView8))
            .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.layoutAddMyWord))
            .check(
                ViewAssertions.matches(
                    withEffectiveVisibility(Visibility.INVISIBLE)
                )
            )
    }

    @Test
    fun checkOpenFragment() {
        Espresso.onView(withId(R.id.buttonAddMyWord)).perform(click())
        Espresso.onView(withId(R.id.buttonCloseAddMyWord))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        isDisplayed(),
                        isClickable()
                    )
                )
            )
        Espresso.onView(withId(R.id.autoCompleteTextViewWord))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        isDisplayed(),
                        isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(withId(R.id.autoCompleteTextViewTranslate))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        isDisplayed(),
                        isClickable(),
                        withText("")
                    )
                )
            )
        Espresso.onView(withId(R.id.buttonAddMyWord1))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        isDisplayed(),
                        isClickable()
                    )
                )
            )
    }

}