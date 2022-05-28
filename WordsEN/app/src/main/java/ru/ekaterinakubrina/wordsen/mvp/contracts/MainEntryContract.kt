package ru.ekaterinakubrina.wordsen.mvp.contracts

import ru.ekaterinakubrina.wordsen.dto.WordDto

interface MainEntryContract {
    interface View {
        fun setWord(newWordDto: WordDto)
        fun setLevel(levelUser: Int)
        fun getName(): String
        fun setName(newNameUser: String)
        fun newLevel()
        fun restartActivity(id: String, level: Int)
        fun initTextToSpeech()
    }

    interface Presenter {
        fun loadData(uid: String)
        fun setLevel(uid: String, newLevel: Int)
        fun getLevelUser(uid: String): Int?
        fun clickOnAlreadyKnowWordButton(uid: String)
        fun deleteNewAndBadStudiedWords(uid: String)
    }

    interface Model{
        fun setLevelLocalAndFirebase(uid: String, level: Int)
        fun getLevelUser(uid: String): Int?
        fun alreadyKnowWord(uid: String, idWord: Int, date: Int)
        fun getWordByDate(uid: String, date: Int): WordDto
        fun getLastDateAddedWord(uid: String): Int?
        fun getWord(uid: String, lvl: Int): WordDto
        fun deleteNewAndBadStudiedWords(uid: String)
        fun addWordToUser(uid: String, word: WordDto): WordDto
        fun getCountNewWord(uid: String): Int?
    }
}