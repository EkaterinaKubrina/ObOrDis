package ru.ekaterinakubrina.wordsen.contracts

interface ResultTestContract {
    interface View {
        fun showResult(str: String)
    }

    interface Presenter {
        fun getResult(result: Int, questionCount: Int)
    }
}