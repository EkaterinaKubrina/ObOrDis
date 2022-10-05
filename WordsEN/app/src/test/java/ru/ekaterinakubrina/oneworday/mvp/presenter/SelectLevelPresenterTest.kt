package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectLevelContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SelectLevelPresenterTest : TestCase() {

    open class SelectLevelActivityMock: SelectLevelContract.View{
        override fun nextActivity(id: String, level: Int) {}
    }

    @Test
    fun testCreated() {
        val selectLevelActivity = SelectLevelActivityMock()
        val presenter =
            SelectLevelPresenter(ApplicationProvider.getApplicationContext(), selectLevelActivity)
        assertNotNull(presenter)
    }
}