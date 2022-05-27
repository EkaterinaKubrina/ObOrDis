package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.data.MyDbHelper
import ru.ekaterinakubrina.wordsen.data.MyDbManager

@RunWith(AndroidJUnit4::class)
class MainEntryActivityTest {
    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), MainEntryActivity::class.java).putExtra(
            "ID_USER",
            "1"
        )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<MainEntryActivity> =
        ActivityScenarioRule(intent)

    @Test
    fun checkCorrectDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.main_word))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.buttonTests))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.buttonDictionary))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.main_level))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.main_name))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.transcription))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.translate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.imageViewSound))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.imageViewSoundSlow))
            .check(
                ViewAssertions.matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isClickable()
                    )
                )
            )
    }
}
