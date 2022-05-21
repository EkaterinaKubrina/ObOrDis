package ru.ekaterinakubrina.wordsen.view

import com.google.firebase.auth.FirebaseAuth

interface EntryContractView {
    fun signIn(auth: FirebaseAuth, email: String, password: String)
}