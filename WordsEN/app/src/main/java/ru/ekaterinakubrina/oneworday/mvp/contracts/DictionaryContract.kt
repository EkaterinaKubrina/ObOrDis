package ru.ekaterinakubrina.oneworday.mvp.contracts

import ru.ekaterinakubrina.oneworday.dto.WordDto
import java.util.*

interface DictionaryContract {
    interface View {
        fun showWords(list: ArrayList<String>)
        fun showText(text: String)
        fun underlineRedWord()
        fun underlineRedTranslate()
        fun clearTextView()
        fun removeUnderline()
    }

    interface Presenter {
        fun getDictionary(uid: String)
        fun clickOnAddWordButton(uid: String, word: String, translate: String, transcription: String)
    }

    interface Model {
        fun getDictionary(uid: String): ArrayList<String>?
        fun addUsersWord(word: WordDto, level: Int, uid: String): Boolean?
        fun getLevelUser(uid: String): Int?
        fun getLanguageUser(uid: String): Int?
    }
}