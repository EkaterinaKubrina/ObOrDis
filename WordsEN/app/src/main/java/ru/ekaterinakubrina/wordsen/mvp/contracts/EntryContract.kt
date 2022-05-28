package ru.ekaterinakubrina.wordsen.mvp.contracts

import ru.ekaterinakubrina.wordsen.dto.UserDto

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
        fun getUserByPasswordAndEmail(email: String, password: String): UserDto?
        fun addUser(uid: String, name: String, email: String, password: String)
        fun setLevel(uid: String?, level: Int?)
        fun addWordFromFB(uid: String, idWord: Int, date: Int, status: Int)
    }
}