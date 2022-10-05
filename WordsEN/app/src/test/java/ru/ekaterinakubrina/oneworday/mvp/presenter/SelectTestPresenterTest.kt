package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectTestContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SelectTestPresenterTest : TestCase() {

    open class SelectTestActivityMock: SelectTestContract.View{
        override fun setAvailableWeekTest() {}
        override fun setNotAvailableWeekTest() {}
        override fun setAvailableAllTest() {}
        override fun setNotAvailableAllTest() {}
    }

    @Test
    fun testCreated() {
        val selectTestActivity = SelectTestActivityMock()
        val presenter =
            SelectTestPresenter(ApplicationProvider.getApplicationContext(), selectTestActivity)
        assertNotNull(presenter)
    }
}