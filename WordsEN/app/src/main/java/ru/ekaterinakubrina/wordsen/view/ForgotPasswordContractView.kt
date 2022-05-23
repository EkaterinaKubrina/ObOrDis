package ru.ekaterinakubrina.wordsen.view

interface ForgotPasswordContractView {
    fun successSend(email: String)
    fun emailNotExist(email: String)
}