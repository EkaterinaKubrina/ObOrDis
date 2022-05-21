package ru.ekaterinakubrina.wordsen.dao

import ru.ekaterinakubrina.wordsen.dto.WordDto

interface WordDao {
    fun getLastDateAddedWord(uid: String): Int?

    fun getWordByDate(uid: String, date: Int): WordDto

    fun addWordToUser(uid: String, lvl: Int): WordDto

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int, status: Int)

    fun deleteNewAndBadStudiedWords(uid: String)

    fun addWordToUserFromFB(uid: String, idWord: Int, date: Int, status: Int)

    fun getCountNewWord(uid: String): Int?

    fun getCountStudiedWord(uid: String): Int?

    fun getUserNewWords(uid: String): ArrayList<WordDto>

    fun getUserStudiedWords(uid: String): ArrayList<WordDto>

    fun getTranslateForTest(rightTranslate: String): ArrayList<String>

    fun setStatusWord(uid: String, idWord: Int, status: Int)

    fun deleteWord(uid: String, idWord: Int)
}