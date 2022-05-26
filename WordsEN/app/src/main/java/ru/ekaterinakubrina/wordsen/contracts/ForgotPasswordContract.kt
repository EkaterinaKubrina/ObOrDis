package ru.ekaterinakubrina.wordsen.contracts

interface ForgotPasswordContract {
    interface View {
        fun successSend(email: String)
        fun emailNotExist(email: String)
    }

    interface Presenter {
        fun sendLetterChangePassword(email: String)
    }
}