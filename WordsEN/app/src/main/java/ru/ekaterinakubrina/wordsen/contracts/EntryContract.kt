package ru.ekaterinakubrina.wordsen.contracts

import ru.ekaterinakubrina.wordsen.dto.UserDto

interface EntryContract {
    interface View {
        fun setError(message: String)
        fun nextActivity(userDto: UserDto)
    }

    interface Presenter {
        fun trySignIn(email: String, password: String)
    }
}