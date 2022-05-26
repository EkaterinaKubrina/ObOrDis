package ru.ekaterinakubrina.wordsen.dao

import ru.ekaterinakubrina.wordsen.dto.WordDto

interface DictionaryDao {
    fun getLastDateAddedWord(uid: String): Int?

    fun getWordByDate(uid: String, date: Int): WordDto

    fun getWord(uid: String, lvl: Int): WordDto

    fun addWordToUser(uid: String, wordDto: WordDto, status: Int)

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int)

    fun deleteNewAndBadStudiedWords(uid: String)

    fun addWordToUserFromFB(uid: String, idWord: Int, date: Int, status: Int)

    fun getCountNewWord(uid: String): Int?

    fun getCountStudiedWord(uid: String): Int?

    fun getUserNewWords(uid: String): ArrayList<WordDto>

    fun getUserStudiedWords(uid: String): ArrayList<WordDto>

    fun setStatusWord(uid: String, idWord: Int, status: Int)

    fun deleteWordFromUser(uid: String, idWord: Int)
}