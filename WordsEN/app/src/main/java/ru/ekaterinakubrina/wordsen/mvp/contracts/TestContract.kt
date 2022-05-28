package ru.ekaterinakubrina.wordsen.mvp.contracts

interface TestContract {
    interface View {
        fun nextActivity(id: String, level: Int)
    }

    interface Presenter {
        fun clickOnEndButton(count: Int, id: String)
    }

    interface Model {
        fun setLevelLocalAndFirebase(uid: String, level: Int)
    }
}