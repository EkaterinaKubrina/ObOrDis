package ru.ekaterinakubrina.wordsen.contracts

interface RegistrationContract {
    interface View {
        fun setTextError(text: String)
        fun underlineRedName()
        fun underlineRedPassword()
        fun underlineRedRepeatPassword()
        fun underlineRedEmail()
        fun successRegistration()
    }

    interface Presenter {
        fun registration(name: String, email: String, password: String, password2: String)
    }
}