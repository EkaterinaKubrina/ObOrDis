package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.mvp.contracts.TestContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TestPresenterTest : TestCase() {

    open class TestActivityMock : TestContract.View{
        override fun nextActivity(id: String, level: Int) {}
    }

    @Test
    fun testCreated() {
        val testActivity = TestActivityMock()
        val presenter =
            TestPresenter(ApplicationProvider.getApplicationContext(), testActivity)
        assertNotNull(presenter)
    }
}