package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.SelectTestActivity
import ru.ekaterinakubrina.wordsen.view.SplashActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SplashPresenterTest : TestCase(){
    private var splashActivity = Mockito.mock(SplashActivity::class.java)

    @Test
    fun testCreated() {
        val presenter =
            SplashPresenter(ApplicationProvider.getApplicationContext(), splashActivity)
        assertNotNull(presenter)
    }
}