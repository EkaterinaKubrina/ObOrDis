package ru.ekaterinakubrina.wordsen.view

import ru.ekaterinakubrina.wordsen.dto.WordDto

interface MainEntryContractView {
    fun setWord(newWordDto: WordDto)
    fun setLevel(levelUser: Int)
    fun setName(newNameUser: String)
    fun newLevel()
    fun initTextToSpeech()
}