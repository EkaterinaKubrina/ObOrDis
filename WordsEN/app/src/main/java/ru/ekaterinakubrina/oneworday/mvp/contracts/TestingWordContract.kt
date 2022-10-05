package ru.ekaterinakubrina.oneworday.mvp.contracts

import ru.ekaterinakubrina.oneworday.dto.WordDto

interface TestingWordContract {
    interface View {
        fun rightAnswer()
        fun wrongAnswer()
        fun nextActivity(uid: String, rightAnswers:Int, listSize: Int)
        fun nextWord(word: String, translate: String, nowIndex: Int, size: Int)
    }

    interface Presenter {
        fun startTest(uid: String, type: String)
        fun getTranslateForTest(rightTranslate: String): ArrayList<String>
        fun checkAnswer(checkedVar: String)
    }

    interface Model{
        fun getTranslateForTest(rightTranslate: String): java.util.ArrayList<String>
        fun getUserNewWords(uid: String): java.util.ArrayList<WordDto>
        fun getUserStudiedWords(uid: String): java.util.ArrayList<WordDto>
        fun setStatusWord(uid: String, idWord: Int, status: Int, statusOld: Int)
        fun deleteWordFromUser(uid: String, idWord: Int)
    }
}