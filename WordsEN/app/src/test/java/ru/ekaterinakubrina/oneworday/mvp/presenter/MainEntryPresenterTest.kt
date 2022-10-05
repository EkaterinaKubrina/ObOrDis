package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import android.widget.PopupMenu
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.dto.UserDto
import ru.ekaterinakubrina.oneworday.mvp.contracts.MainEntryContract
import ru.ekaterinakubrina.oneworday.dto.WordDto


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
        override fun initTextToSpeech(language: Int) {}
        override fun setAlarm(userDto: UserDto) {}
        override fun changeLanguageEnable(language: Int, pop: PopupMenu) {}
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
        Mockito.doNothing().`when`(mainEntryPresenterSpy).setWordDay("", MyDbWordsEN.Language.ENGLISH)
        Mockito.doReturn(0).`when`(mainEntryPresenterSpy).getLevelUser("")
        mainEntryPresenterSpy.loadData("")
    }


    @Test
    fun getLevelUser() {
        val mainEntryPresenterSpy = Mockito.spy(mainEntryPresenter)
        assertEquals(null, mainEntryPresenterSpy.getLevelUser("1"))
    }
}