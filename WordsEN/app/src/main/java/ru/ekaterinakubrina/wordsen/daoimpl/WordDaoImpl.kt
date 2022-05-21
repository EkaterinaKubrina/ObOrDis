package ru.ekaterinakubrina.wordsen.daoimpl

import android.content.Context
import ru.ekaterinakubrina.wordsen.dao.WordDao
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*


class WordDaoImpl(context: Context) : WordDao {
    private val myDbManager = MyDbManager(context)

    override fun getLastDateAddedWord(uid: String): Int? {
        myDbManager.openDb()
        val date = myDbManager.getLastDate(uid)
        myDbManager.closeDb()
        return date
    }


    override fun getWordByDate(uid: String, date: Int): WordDto {
        myDbManager.openDb()
        val word = myDbManager.getWordByDate(uid, date)
        myDbManager.closeDb()
        return word
    }

    override fun addWordToUser(uid: String, lvl: Int): WordDto {
        myDbManager.openDb()
        val wordDto: WordDto = myDbManager.getWord(uid, lvl)
        myDbManager.insertWordToUser(
            uid,
            wordDto.wordId,
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt(),
            MyDbWordsEN.UsersWords.NEW
        )
        myDbManager.closeDb()
        return wordDto
    }


    override fun addWordToUserFromFB(uid: String, idWord: Int, date: Int, status: Int) {
        myDbManager.openDb()
        myDbManager.insertWordToUser(uid, idWord, date, status)
        myDbManager.closeDb()
    }

    override fun getCountNewWord(uid: String): Int? {
        myDbManager.openDb()
        val count: Int? = myDbManager.getCountNewWord(uid)
        myDbManager.closeDb()
        return count
    }

    override fun getCountStudiedWord(uid: String): Int? {
        myDbManager.openDb()
        val count: Int? = myDbManager.getCountStudiedWord(uid)
        myDbManager.closeDb()
        return count
    }

    override fun getUserNewWords(uid: String): ArrayList<WordDto> {
        myDbManager.openDb()
        val list: ArrayList<WordDto> = myDbManager.getUserNewAndBadStudyWords(uid)
        myDbManager.closeDb()
        return list
    }

    override fun getUserStudiedWords(uid: String): ArrayList<WordDto> {
        myDbManager.openDb()
        val list: ArrayList<WordDto> = myDbManager.getUserStudiedWords(uid)
        myDbManager.closeDb()
        return list
    }

    override fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        myDbManager.openDb()
        val list: ArrayList<String> = myDbManager.getTranslateForTest(rightTranslate)
        myDbManager.closeDb()
        return list
    }

    override fun setStatusWord(uid: String, idWord: Int, status: Int) {
        myDbManager.openDb()
        myDbManager.setStatusWord(uid, idWord, status)
        myDbManager.closeDb()
    }

    override fun deleteWord(uid: String, idWord: Int) {
        myDbManager.openDb()
        myDbManager.deleteWord(uid, idWord)
        myDbManager.closeDb()
    }

    override fun alreadyKnowWord(uid: String, idWord: Int, date: Int, status: Int) {
        myDbManager.openDb()
        myDbManager.alreadyKnowWord(uid, idWord, date, status)
        myDbManager.closeDb()
    }

    override fun deleteNewAndBadStudiedWords(uid: String) {
        myDbManager.openDb()
        myDbManager.deleteNewAndBadStudiedWords(uid)
        myDbManager.closeDb()
    }


}