package ru.ekaterinakubrina.wordsen.contracts

interface SelectLevelContract {
    interface View {
        fun nextActivity(id: String, level: Int)
    }

    interface Presenter {
        fun selectLevel(id: String, level: Int)
    }
}