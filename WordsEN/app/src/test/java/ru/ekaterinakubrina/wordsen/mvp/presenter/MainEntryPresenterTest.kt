package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.mvp.contracts.MainEntryContract
import ru.ekaterinakubrina.wordsen.dto.WordDto


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class MainEntryPresenterTest : TestCase() {
    open class MainEntryActivityMock : MainEntryContract.View {
        override fun setWord(newWordDto: WordDto) {}
        override fun setLevel(levelUser: Int) {}
        override fun getName(): String {
            return ""
        }
        override fun setName(newNameUser: String) {}
        override fun newLevel() {}
        override fun restartActivity(id: String, level: Int) {}
        override fun initTextToSpeech() {}
    }

    private var mainEntryActivity = Mockito.mock(MainEntryActivityMock::class.java)
    private var mainEntryPresenter = MainEntryPresenter(
        ApplicationProvider.getApplicationContext(),
        mainEntryActivity
    )

    @Test
    fun testCreated() {
        assertNotNull(mainEntryPresenter)
    }

    @Test
    fun loadData() {
        val mainEntryPresenterSpy = Mockito.spy(mainEntryPresenter)
        Mockito.doNothing().`when`(mainEntryPresenterSpy).setWordDay("")
        Mockito.doReturn(0).`when`(mainEntryPresenterSpy).getLevelUser("")
        mainEntryPresenterSpy.loadData("")

        Mockito.verify(mainEntryActivity)?.initTextToSpeech()
    }


    @Test
    fun getLevelUser() {
        val mainEntryPresenterSpy = Mockito.spy(mainEntryPresenter)
        assertEquals(1, mainEntryPresenterSpy.getLevelUser("1"))
    }
}