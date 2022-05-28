package ru.ekaterinakubrina.wordsen.daoimpl

import ru.ekaterinakubrina.wordsen.dao.WordDao
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.util.*


class WordDaoImpl(private val myDbManager: MyDbManager) : WordDao {

    override fun getIdByWord(word: String): Int {
        myDbManager.openDb()
        val id: Int = myDbManager.getIdByWord(word)
        myDbManager.closeDb()
        return id
    }

    override fun addWord(wordDto: WordDto, level: Int): Long? {
        myDbManager.openDb()
        val id: Long? = myDbManager.addWord(wordDto, level)
        myDbManager.closeDb()
        return id
    }

    override fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        myDbManager.openDb()
        val list: ArrayList<String> = myDbManager.getTranslateForTest(rightTranslate)
        myDbManager.closeDb()
        return list
    }

    override fun deleteWord(idWord: Int) {
        myDbManager.openDb()
        myDbManager.deleteWord(idWord)
        myDbManager.closeDb()
    }

    override fun getWordById(wordId: Int): WordDto? {
        myDbManager.openDb()
        val wordDto = myDbManager.getWordById(wordId)
        myDbManager.closeDb()
        return wordDto
    }

}