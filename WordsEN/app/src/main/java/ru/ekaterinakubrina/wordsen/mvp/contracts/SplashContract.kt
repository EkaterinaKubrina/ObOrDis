package ru.ekaterinakubrina.wordsen.mvp.contracts

import ru.ekaterinakubrina.wordsen.dto.UserDto

interface SplashContract {
    interface View {
        fun authorized(uid: String)
        fun notAuthorized()
    }

    interface Presenter {
        fun checkAuth()
    }

    interface Model{
        fun getUser(uid: String): UserDto?
    }
}