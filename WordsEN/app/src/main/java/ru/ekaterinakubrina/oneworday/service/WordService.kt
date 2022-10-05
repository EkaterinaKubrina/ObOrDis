package ru.ekaterinakubrina.oneworday.service

import ru.ekaterinakubrina.oneworday.dao.WordDao
import ru.ekaterinakubrina.oneworday.dto.WordDto
import java.util.*

open class WordService(private val wordDao: WordDao) {

    fun addWord(word: WordDto, level: Int): Long? {
        return wordDao.addWord(word, level)
    }

    fun deleteWord(wordId: Int) {
        wordDao.deleteWord(wordId)
    }

    fun getWordById(wordId: Int): WordDto? {
        return wordDao.getWordById(wordId)
    }

    fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        return wordDao.getTranslateForTest(rightTranslate)
    }

    fun getIdByWord(word: String) : Int{
        return wordDao.getIdByWord(word)
    }

}