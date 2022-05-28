package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.mvp.contracts.SplashContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SplashPresenterTest : TestCase() {

    open class SplashActivityMock: SplashContract.View{
        override fun authorized(uid: String) {}
        override fun notAuthorized() {}
    }

    @Test
    fun testCreated() {
        val splashActivity = SplashActivityMock()
        val presenter =
            SplashPresenter(ApplicationProvider.getApplicationContext(), splashActivity)
        assertNotNull(presenter)
    }
}