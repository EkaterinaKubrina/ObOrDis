package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.SplashActivity
import ru.ekaterinakubrina.wordsen.view.TestingWordActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TestingWordPresenterTest : TestCase() {
    private var testingWordActivity = Mockito.mock(TestingWordActivity::class.java)
    private var testingWordPresenter = TestingWordPresenter(
        ApplicationProvider.getApplicationContext(),
        testingWordActivity
    )

    @Test
    fun testCreated() {
        val presenter =
            TestingWordPresenter(ApplicationProvider.getApplicationContext(), testingWordActivity)
        assertNotNull(presenter)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun shouldThrow() {
        testingWordPresenter.startTest("", "")
    }
}