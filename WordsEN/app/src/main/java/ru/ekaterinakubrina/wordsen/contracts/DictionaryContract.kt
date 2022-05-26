package ru.ekaterinakubrina.wordsen.contracts

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
        fun addUsersWord(uid: String, word: String, translate: String, transcription: String)
    }
}