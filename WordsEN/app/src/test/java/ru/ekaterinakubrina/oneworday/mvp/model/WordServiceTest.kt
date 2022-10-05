package ru.ekaterinakubrina.oneworday.mvp.model

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase

import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.oneworday.data.MyDbManager
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.dto.WordDto
import ru.ekaterinakubrina.oneworday.service.WordService

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class WordServiceTest : TestCase() {
    private val wordsModel = WordService(WordDaoImpl(MyDbManager(ApplicationProvider.getApplicationContext())))

    @Test
    fun getTranslateForTest() {
        val words = wordsModel.getTranslateForTest("быть")
        assertEquals(words.size, 3)
        assertEquals(words.contains("быть"), false)
    }

    @Test
    fun addAndDeleteWordTest() {
        val wordDto = WordDto(0, "cat", "кот", "transcription", MyDbWordsEN.Language.ENGLISH)
        val wordId = wordsModel.addWord(wordDto, MyDbWordsEN.Level.LEVEL_A0)

        var word = wordsModel.getWordById(wordId!!.toInt())
        assertEquals(word?.word, "cat")

        wordsModel.deleteWord(wordId.toInt())

        word = wordsModel.getWordById(wordId.toInt())
        assertEquals(word, null)
    }

    @Test
    fun getWordByIdTest() {
        val word = wordsModel.getWordById(1)
        assertEquals(word?.word, "to be" )
        assertEquals(word?.translate, "быть" )
    }

    @Test
    fun getIdByWordTest() {
        val id = wordsModel.getIdByWord("to be")
        assertEquals(id, 1)
    }

}