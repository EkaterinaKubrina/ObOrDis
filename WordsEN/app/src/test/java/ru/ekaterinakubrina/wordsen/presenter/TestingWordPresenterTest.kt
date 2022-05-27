package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.TestingWordActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TestingWordPresenterTest : TestCase() {

    @Test
    fun testCreated() {
        val testingWordActivity = TestingWordActivity()
        val presenter =
            TestingWordPresenter(ApplicationProvider.getApplicationContext(), testingWordActivity)
        assertNotNull(presenter)
    }

}