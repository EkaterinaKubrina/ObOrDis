package ru.ekaterinakubrina.oneworday.mvp.contracts

interface TestContract {
    interface View {
        fun nextActivity(id: String, level: Int)
        fun setWords(list: List<String>)
    }

    interface Presenter {
        fun clickOnEndButton(count: Int, id: String)
        fun initTest(language: Int)
    }

    interface Model {
        fun setLevelLocalAndFirebase(uid: String, level: Int)
    }
}