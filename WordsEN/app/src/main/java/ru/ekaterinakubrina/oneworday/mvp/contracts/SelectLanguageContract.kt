package ru.ekaterinakubrina.oneworday.mvp.contracts

import java.util.ArrayList

interface SelectLanguageContract {
    interface View {
        fun nextActivity()
        fun initSelectSpinner(data: ArrayList<String>)
    }

    interface Presenter {
        fun clickOnOkButton(uid: String, language: Int)
        fun initLanguages()
    }

    interface Model {
        fun setUserLanguage(uid: String, newLanguage: Int)
        fun getLanguages(): ArrayList<String>
    }
}