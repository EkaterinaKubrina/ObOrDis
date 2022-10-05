package ru.ekaterinakubrina.oneworday.mvp.contracts

import android.widget.PopupMenu
import ru.ekaterinakubrina.oneworday.dto.UserDto
import ru.ekaterinakubrina.oneworday.dto.WordDto

interface MainEntryContract {
    interface View {
        fun setWord(newWordDto: WordDto)
        fun setLevel(levelUser: Int)
        fun getName(): String
        fun setName(newNameUser: String)
        fun newLevel()
        fun initTextToSpeech(language: Int)
        fun setAlarm(userDto: UserDto)
        fun changeLanguageEnable(language: Int, pop: PopupMenu)
    }

    interface Presenter {
        fun loadData(uid: String)
        fun setLevel(uid: String, newLevel: Int)
        fun getLevelUser(uid: String): Int?
        fun clickOnAlreadyKnowWordButton(uid: String)
        fun setLanguage(uid: String, newLanguage: Int)
        fun enableLanguage(uid: String, pop: PopupMenu)
    }

    interface Model{
        fun setLevelLocalAndFirebase(uid: String, level: Int)
        fun getLevelUser(uid: String): Int?
        fun alreadyKnowWord(uid: String, idWord: Int, date: Int)
        fun getWordByDate(uid: String, date: Int): WordDto
        fun getLastDateAddedWord(uid: String): Int?
        fun getWord(uid: String, lvl: Int, language: Int): WordDto
        fun deleteNewAndBadStudiedWords(uid: String)
        fun addWordToUser(uid: String, word: WordDto): WordDto
        fun getCountNewWord(uid: String): Int?
        fun setUserLanguage(uid: String, language: Int)
        fun getUser(uid: String): UserDto?
        fun getLanguageUser(uid: String): Int?
    }
}