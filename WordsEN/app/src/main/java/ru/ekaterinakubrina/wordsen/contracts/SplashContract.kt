package ru.ekaterinakubrina.wordsen.contracts

interface SplashContract {
    interface View {
        fun authorized(uid: String)
        fun notAuthorized()
    }

    interface Presenter {
        fun checkAuth()
    }
}