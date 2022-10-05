package ru.ekaterinakubrina.oneworday.dao

import ru.ekaterinakubrina.oneworday.dto.WordDto

interface DictionaryDao {
    fun getLastDateAddedWord(uid: String, language: Int): Int?

    fun getWordByDate(uid: String, date: Int): WordDto

    fun getWord(uid: String, lvl: Int, language: Int): WordDto

    fun addWordToUser(uid: String, wordDto: WordDto, status: Int)

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int)

    fun deleteNewAndBadStudiedWords(uid: String)

    fun addWordToUserFromFB(uid: String, idWord: Int, date: Int, status: Int)

    fun getCountNewWord(uid: String): Int?

    fun getCountStudiedWord(uid: String, language: Int): Int?

    fun getUserNewWords(uid: String): ArrayList<WordDto>

    fun getUserStudiedWords(uid: String, language: Int): ArrayList<WordDto>

    fun setStatusWord(uid: String, idWord: Int, status: Int)

    fun deleteWordFromUser(uid: String, idWord: Int)

    fun getLanguages(): ArrayList<String>
}