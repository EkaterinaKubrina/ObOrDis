package ru.ekaterinakubrina.oneworday.mvp.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.RunWith
import androidx.test.core.app.ActivityScenario.launch
import com.google.firebase.auth.FirebaseAuth


@RunWith(AndroidJUnit4::class)
class SplashActivityTest  {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        SplashActivity::class.java
    )

    @get:Rule
    val activityTestRule: ActivityScenarioRule<SplashActivity> =
        ActivityScenarioRule(intent)


    companion object{
        @Before
        fun setUp() {
            Intents.init()
        }
        @After
        fun tearDown() {
            Intents.release()
        }
    }

    @Test
    fun checkStartActivity() {
        setUp()
        launch(SplashActivity::class.java)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            intended(hasComponent(MainEntryActivity::class.java.name))
        }
        else {
            intended(hasComponent(MainActivity::class.java.name))
        }
        tearDown()
    }
}