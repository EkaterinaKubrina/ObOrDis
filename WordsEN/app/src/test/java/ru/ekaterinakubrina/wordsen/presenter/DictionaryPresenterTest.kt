package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build.VERSION_CODES.Q
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.contracts.DictionaryContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
internal class DictionaryPresenterTest : TestCase() {
    open class DictionaryActivityMock : DictionaryContract.View {
        override fun showWords(list: java.util.ArrayList<String>) {}

        override fun showText(text: String) {}

        override fun underlineRedWord() {}

        override fun underlineRedTranslate() {}

        override fun clearTextView() {}

        override fun removeUnderline() {}
    }

    private var dictionaryActivity = mock(DictionaryActivityMock::class.java)
    private var dictionaryPresenter = DictionaryPresenter(
        ApplicationProvider.getApplicationContext(),
        dictionaryActivity
    )

    @Test
    fun testCreated() {
        assertNotNull(dictionaryPresenter)
    }

    @Test
    fun getDictionary() {
        dictionaryPresenter.getDictionary("")
        verify(dictionaryActivity)?.showWords(ArrayList())
    }

    @Test
    fun addUsersNullWord() {
        dictionaryPresenter.addUsersWord("", "", "", "")
        verify(dictionaryActivity)?.underlineRedWord()
        verify(dictionaryActivity)?.showText("Поля не могут быть пустыми")
    }

    @Test
    fun addUsersBadWord() {
        dictionaryPresenter.addUsersWord("", "123", "", "")
        verify(dictionaryActivity)?.underlineRedWord()
        verify(dictionaryActivity)
            ?.showText("Слово не может содержать цифры, знаки и русские буквы")
    }

    @Test
    fun addUsersWordNullTranslate() {
        dictionaryPresenter.addUsersWord("", "qwerty", "", "")
        verify(dictionaryActivity)?.underlineRedTranslate()
        verify(dictionaryActivity)?.showText("Поля не могут быть пустыми")
    }

    @Test
    fun addUsersWordBadTranslate() {
        dictionaryPresenter.addUsersWord("", "qwerty", "123", "")
        verify(dictionaryActivity)?.underlineRedTranslate()
        verify(dictionaryActivity)?.showText("Перевод должен быть на русском языке")
    }

}