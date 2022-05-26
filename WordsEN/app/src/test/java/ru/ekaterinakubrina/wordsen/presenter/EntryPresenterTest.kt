package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.EntryActivity


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class EntryPresenterTest : TestCase() {

    private var entryActivity = mock(EntryActivity::class.java)

    @Test
    fun testCreated() {
        val presenter =
            EntryPresenter(ApplicationProvider.getApplicationContext(), entryActivity)
        assertNotNull(presenter)
    }
}