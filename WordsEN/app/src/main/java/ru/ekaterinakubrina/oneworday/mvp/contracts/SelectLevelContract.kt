package ru.ekaterinakubrina.oneworday.mvp.contracts

interface SelectLevelContract {
    interface View {
        fun nextActivity(id: String, level: Int)
    }

    interface Presenter {
        fun selectLevel(id: String, level: Int)
    }

    interface Model{
        fun setLevelLocalAndFirebase(uid: String, level: Int)
    }
}