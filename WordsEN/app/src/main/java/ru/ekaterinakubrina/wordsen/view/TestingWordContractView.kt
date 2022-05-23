package ru.ekaterinakubrina.wordsen.view

interface TestingWordContractView {
    fun rightAnswer()
    fun wrongAnswer()
    fun nextActivity(uid: String, rightAnswers:Int, listSize: Int)
    fun nextWord(word: String, translate: String, nowIndex: Int, size: Int)
}