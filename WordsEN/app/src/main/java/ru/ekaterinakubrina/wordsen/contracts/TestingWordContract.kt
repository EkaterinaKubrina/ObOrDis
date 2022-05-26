package ru.ekaterinakubrina.wordsen.contracts

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
}