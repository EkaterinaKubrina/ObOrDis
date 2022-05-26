package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.MainEntryActivity


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class MainEntryPresenterTest : TestCase() {
    private var mainEntryActivity = Mockito.mock(MainEntryActivity::class.java)
    private var mainEntryPresenter = MainEntryPresenter(
        ApplicationProvider.getApplicationContext(),
        mainEntryActivity
    )

    @Test
    fun testCreated() {
        val presenter =
            MainEntryPresenter(ApplicationProvider.getApplicationContext(), mainEntryActivity)
        assertNotNull(presenter)
    }

    @Test
    fun loadData() {
        val mainEntryPresenterSpy = Mockito.spy(mainEntryPresenter)
        Mockito.doNothing().`when`(mainEntryPresenterSpy).setWordDay("")
        Mockito.doReturn(0).`when`(mainEntryPresenterSpy).getLevelUser("")
        mainEntryPresenterSpy.loadData("")

        Mockito.verify(mainEntryActivity)?.initTextToSpeech()
        Mockito.verify(mainEntryActivity)?.setName(anyOrNull())
        Mockito.verify(mainEntryActivity)?.setLevel(anyOrNull())
    }
}