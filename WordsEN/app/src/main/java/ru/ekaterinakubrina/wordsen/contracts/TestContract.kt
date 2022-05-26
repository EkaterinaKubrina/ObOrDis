package ru.ekaterinakubrina.wordsen.contracts

interface TestContract {
    interface View {
        fun nextActivity(id: String, level: Int)
    }

    interface Presenter {
        fun setLevel(count: Int, id: String)
    }
}