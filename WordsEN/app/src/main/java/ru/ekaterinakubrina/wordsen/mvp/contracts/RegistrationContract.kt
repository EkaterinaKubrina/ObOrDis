package ru.ekaterinakubrina.wordsen.mvp.contracts

interface RegistrationContract {
    interface View {
        fun setTextError(text: String)
        fun underlineRedName()
        fun underlineRedPassword()
        fun underlineRedRepeatPassword()
        fun underlineRedEmail()
        fun successRegistration()
        fun getName(): String
        fun getEmail(): String
        fun getPassword(): String
        fun getPassword2(): String
    }

    interface Presenter {
        fun clickOnRegistrationButton()
    }

    interface Model{
        fun addUserLocalAndFirebase(uid: String, name: String, email: String, password: String)
    }
}