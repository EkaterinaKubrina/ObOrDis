package ru.ekaterinakubrina.oneworday.mvp.contracts

interface ForgotPasswordContract {
    interface View {
        fun successSend(email: String)
        fun emailNotExist(email: String)
        fun getEmail(): String
    }

    interface Presenter {
        fun clickOnSendNewPasswordButton()
    }
}