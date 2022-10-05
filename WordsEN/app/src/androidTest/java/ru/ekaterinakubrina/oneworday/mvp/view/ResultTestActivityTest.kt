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
class ResultTestActivityTest : TestCase(){
    private val intent = Intent(ApplicationProvider.getApplicationContext(), ResultTestActivity::class.java).putExtra(
        "ID_USER",
        "1"
    ).putExtra(
        "RESULT",
        10
    ).putExtra(
        "QUESTIONS",
        14
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<ResultTestActivity> =
        ActivityScenarioRule(intent)


    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.imageView16))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.buttonOk))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.resultText))
            .check(
                ViewAssertions.matches(
                    withText("Твой результат - 10/14. Молодец!")
                )
            )
    }
}