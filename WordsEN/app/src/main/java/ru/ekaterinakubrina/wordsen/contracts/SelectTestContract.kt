package ru.ekaterinakubrina.wordsen.contracts

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
}