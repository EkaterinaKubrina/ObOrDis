package ru.ekaterinakubrina.oneworday.mvp.contracts

interface ResultTestContract {
    interface View {
        fun showResult(str: String)
    }

    interface Presenter {
        fun getResult(result: Int, questionCount: Int)
    }
}