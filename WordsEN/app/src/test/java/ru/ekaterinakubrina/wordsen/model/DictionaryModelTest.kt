package ru.ekaterinakubrina.wordsen.model

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class DictionaryModelTest : TestCase() {
    private val wordsModel = WordModel(WordDaoImpl(ApplicationProvider.getApplicationContext()))
    private val dictionaryModel =
        DictionaryModel(DictionaryDaoImpl(ApplicationProvider.getApplicationContext()), wordsModel)
    private val dictionaryModelSpy = Mockito.spy(dictionaryModel)

    @Before
    fun setBefore() {
        Mockito.doNothing().`when`(dictionaryModelSpy).saveNewWordToFirebase(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )
        Mockito.doNothing().`when`(dictionaryModelSpy).deleteWordFirebase(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
        )
        Mockito.doNothing().`when`(dictionaryModelSpy).saveStudiedWordToFirebase(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )
        Mockito.doNothing().`when`(dictionaryModelSpy).deleteNewWordFirebase(
            ArgumentMatchers.anyString()
        )
        Mockito.doNothing().`when`(dictionaryModelSpy).saveNewStatusToFirebase(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )
    }

    @Test
    fun getWord() {
        val word = dictionaryModel.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        assertEquals(MyDbWordsEN.Dictionary.NEW, word.status)
        assertEquals("to be", word.word)
        assertEquals("быть", word.translate)
        assertEquals("[tə biː]", word.transcription)
    }

    @Test
    fun addWordToUserTest() {
        assertEquals(dictionaryModelSpy.getCountNewWord("1"), 0)

        val wordDto = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", wordDto)

        assertEquals(1, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", wordsModel.getIdByWord("cat"))
    }

    @Test
    fun addUsersWordTest() {
        assertEquals(dictionaryModelSpy.getCountStudiedWord("1"), 0)

        val wordDto = WordDto(0, "cat", "кот", "transcription")
        dictionaryModelSpy.addUsersWord(wordDto, 1, "1")

        assertEquals(1, dictionaryModelSpy.getCountStudiedWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", wordsModel.getIdByWord("cat"))
    }

    @Test
    fun getLastDateAddedWordTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        assertEquals(
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt(),
            dictionaryModelSpy.getLastDateAddedWord("1")
        )

        dictionaryModelSpy.deleteWordFromUser("1", wordsModel.getIdByWord(word.word))
    }

    @Test
    fun getWordByDateTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        val wordByDate = dictionaryModelSpy.getWordByDate("1", SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt())

        assertEquals(word.word, wordByDate.word)
        assertEquals(word.translate, wordByDate.translate)
    }

    @Test
    fun alreadyKnowWordTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        dictionaryModelSpy.alreadyKnowWord("1", word.wordId, SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()-1)

        assertEquals(1, dictionaryModelSpy.getCountStudiedWord("1"))
        assertEquals(0, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", 1)
        dictionaryModelSpy.deleteWordFromUser("1", 2)
    }

    @Test
    fun deleteNewAndBadStudiedWordsTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        assertEquals(1, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.deleteNewAndBadStudiedWords("1")

        assertEquals(0, dictionaryModelSpy.getCountNewWord("1"))
        dictionaryModelSpy.deleteWordFromUser("1", 1)
    }

    @Test
    fun setStatusWordTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        assertEquals(1, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.setStatusWord("1", word.wordId, MyDbWordsEN.Dictionary.STUDIED,  MyDbWordsEN.Dictionary.NEW)

        assertEquals(0, dictionaryModelSpy.getCountNewWord("1"))
        assertEquals(1, dictionaryModelSpy.getCountStudiedWord("1"))
        dictionaryModelSpy.deleteWordFromUser("1", 1)
    }

    @Test
    fun deleteWordFromUserTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        assertEquals(1, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", word.wordId)

        assertEquals(0, dictionaryModelSpy.getCountNewWord("1"))
    }

    @Test
    fun getCountNewWordTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)

        assertEquals(1, dictionaryModelSpy.getCountNewWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", word.wordId)

        assertEquals(0, dictionaryModelSpy.getCountNewWord("1"))
    }

    @Test
    fun getCountStudiedWordTest() {
        val word = dictionaryModelSpy.getWord("1", MyDbWordsEN.Users.LEVEL_A0)
        dictionaryModelSpy.addWordToUser("1", word)
        dictionaryModelSpy.setStatusWord("1", word.wordId, MyDbWordsEN.Dictionary.STUDIED,  MyDbWordsEN.Dictionary.NEW)

        assertEquals(1, dictionaryModelSpy.getCountStudiedWord("1"))

        dictionaryModelSpy.deleteWordFromUser("1", word.wordId)

        assertEquals(0, dictionaryModelSpy.getCountStudiedWord("1"))
    }

}