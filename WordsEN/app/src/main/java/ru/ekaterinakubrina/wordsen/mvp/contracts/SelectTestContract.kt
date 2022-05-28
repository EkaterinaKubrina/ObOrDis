package ru.ekaterinakubrina.wordsen.mvp.contracts

interface SelectTestContract {
    interface View {
        fun setAvailableWeekTest()
        fun setNotAvailableWeekTest()
        fun setAvailableAllTest()
        fun setNotAvailableAllTest()
    }

    interface Presenter {
        fun availableTest(uid: String)
    }

    interface Model{
        fun getCountNewWord(uid: String): Int?
        fun getCountStudiedWord(uid: String): Int?
    }
}