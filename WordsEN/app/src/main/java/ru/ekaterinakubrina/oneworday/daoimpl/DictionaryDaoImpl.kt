package ru.ekaterinakubrina.oneworday.daoimpl

import ru.ekaterinakubrina.oneworday.dao.DictionaryDao
import ru.ekaterinakubrina.oneworday.data.MyDbManager
import ru.ekaterinakubrina.oneworday.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*

class DictionaryDaoImpl(private val myDbManager: MyDbManager) : DictionaryDao {

    override fun getLastDateAddedWord(uid: String, language: Int): Int? {
        myDbManager.openDb()
        val date = myDbManager.getLastDateAddedWord(uid, language)
        myDbManager.closeDb()
        return date
    }

    override fun getWordByDate(uid: String, date: Int): WordDto {
        myDbManager.openDb()
        val word = myDbManager.getUsersWordByDate(uid, date, myDbManager.getLanguageUser(uid)!!)
        myDbManager.closeDb()
        return word
    }

    override fun addWordToUser(uid: String, wordDto: WordDto, status: Int) {
        myDbManager.openDb()
        myDbManager.insertWordToUser(
            uid,
            wordDto.wordId,
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt(),
            status
        )
        myDbManager.closeDb()
    }

    override fun getWord(uid: String, lvl: Int, language: Int): WordDto {
        myDbManager.openDb()
        val wordDto: WordDto = myDbManager.getWordForUserByLevel(uid, lvl, language)
        myDbManager.closeDb()
        return wordDto
    }

    override fun getLanguages(): ArrayList<String>{
        myDbManager.openDb()
        val list = myDbManager.getLanguages()
        myDbManager.closeDb()
        return list
    }

    override fun addWordToUserFromFB(uid: String, idWord: Int, date: Int, status: Int) {
        myDbManager.openDb()
        myDbManager.insertWordToUser(uid, idWord, date, status)
        myDbManager.closeDb()
    }

    override fun getCountNewWord(uid: String): Int? {
        myDbManager.openDb()
        val count: Int? = myDbManager.getCountNewWord(uid, myDbManager.getLanguageUser(uid)!!)
        myDbManager.closeDb()
        return count
    }

    override fun getCountStudiedWord(uid: String, language: Int): Int? {
        myDbManager.openDb()
        val count: Int? = myDbManager.getCountStudiedWord(uid, language)
        myDbManager.closeDb()
        return count
    }

    override fun getUserNewWords(uid: String): ArrayList<WordDto> {
        myDbManager.openDb()
        val list: ArrayList<WordDto> = myDbManager.getUserNewAndBadStudyWords(uid, myDbManager.getLanguageUser(uid)!!)
        myDbManager.closeDb()
        return list
    }

    override fun getUserStudiedWords(uid: String, language: Int): ArrayList<WordDto> {
        myDbManager.openDb()
        val list: ArrayList<WordDto> = myDbManager.getUserStudiedWords(uid, language)
        myDbManager.closeDb()
        return list
    }

    override fun setStatusWord(uid: String, idWord: Int, status: Int) {
        myDbManager.openDb()
        myDbManager.setStatusWord(uid, idWord, status)
        myDbManager.closeDb()
    }

    override fun deleteWordFromUser(uid: String, idWord: Int) {
        myDbManager.openDb()
        myDbManager.deleteWordFromUser(uid, idWord)
        myDbManager.closeDb()
    }

    override fun alreadyKnowWord(uid: String, idWord: Int, date: Int) {
        myDbManager.openDb()
        myDbManager.alreadyKnowWord(uid, idWord, date)
        myDbManager.closeDb()
    }

    override fun deleteNewAndBadStudiedWords(uid: String) {
        myDbManager.openDb()
        myDbManager.deleteNewAndBadStudiedWords(uid)
        myDbManager.closeDb()
    }

}