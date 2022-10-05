package ru.ekaterinakubrina.oneworday.mvp.contracts

import ru.ekaterinakubrina.oneworday.dto.UserDto

interface EntryContract {
    interface View {
        fun setError(message: String)
        fun nextActivity(userDto: UserDto)
        fun setMyProgressBarVisibility(visible: Boolean)
        fun getEmail(): String
        fun getPassword(): String
    }

    interface Presenter {
        fun clickOnEntryButton()
    }

    interface Model {
        fun getUser(uid: String): UserDto?
        fun addUser(uid: String, name: String)
        fun setLevel(uid: String?, level: Int?)
        fun addWordFromFB(uid: String, idWord: Int, date: Int, status: Int)
        fun setUserLanguage(uid: String, newLanguage: Int)
    }
}