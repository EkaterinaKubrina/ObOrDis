package ru.ekaterinakubrina.wordsen.contracts

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
        fun alreadyKnowWord(uid: String)
        fun deleteNewAndBadStudiedWords(uid: String)
    }
}