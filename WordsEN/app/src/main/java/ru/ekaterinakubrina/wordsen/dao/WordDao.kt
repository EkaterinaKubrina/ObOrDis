package ru.ekaterinakubrina.wordsen.dao

import ru.ekaterinakubrina.wordsen.dto.WordDto

interface WordDao {
    fun getIdByWord(word: String): Int

    fun addWord(wordDto: WordDto, level: Int): Long?

    fun getTranslateForTest(rightTranslate: String): ArrayList<String>

    fun deleteWord(idWord: Int)

    fun getWordById(wordId: Int): WordDto?
}