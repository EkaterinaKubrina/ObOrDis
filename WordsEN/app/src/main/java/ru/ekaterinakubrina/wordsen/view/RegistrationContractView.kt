package ru.ekaterinakubrina.wordsen.view

interface RegistrationContractView {
    fun setTextError(text: String)
    fun underlineRedName()
    fun underlineRedPassword()
    fun underlineRedRepeatPassword()
    fun underlineRedEmail()
    fun successRegistration()
}