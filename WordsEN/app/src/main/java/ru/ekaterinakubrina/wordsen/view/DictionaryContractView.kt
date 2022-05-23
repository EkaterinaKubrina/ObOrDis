package ru.ekaterinakubrina.wordsen.view

import java.util.ArrayList

interface DictionaryContractView {
    fun showWords(list: ArrayList<String>)
    fun showText(text: String)
    fun underlineRedWord()
    fun underlineRedTranslate()
    fun clearTextView()
    fun removeUnderline()
}