package ru.ekaterinakubrina.oneworday.dao

import ru.ekaterinakubrina.oneworday.dto.WordDto

interface WordDao {
    fun getIdByWord(word: String): Int

    fun addWord(wordDto: WordDto, level: Int): Long?

    fun getTranslateForTest(rightTranslate: String): ArrayList<String>

    fun deleteWord(idWord: Int)

    fun getWordById(wordId: Int): WordDto?
}