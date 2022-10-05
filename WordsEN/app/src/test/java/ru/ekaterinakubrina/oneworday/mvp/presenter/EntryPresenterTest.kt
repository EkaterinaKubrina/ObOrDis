package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.EntryContract
import ru.ekaterinakubrina.oneworday.dto.UserDto


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class EntryPresenterTest : TestCase() {

    open class EntryActivityMock: EntryContract.View{
        override fun setError(message: String) {}
        override fun nextActivity(userDto: UserDto) {}
        override fun setMyProgressBarVisibility(visible: Boolean) {}
        override fun getEmail(): String {
            return ""
        }
        override fun getPassword(): String {
            return ""
        }
    }

    @Test
    fun testCreated() {
        val entryActivity = EntryActivityMock()
        val presenter =
            EntryPresenter(ApplicationProvider.getApplicationContext(), entryActivity)
        assertNotNull(presenter)
    }
}