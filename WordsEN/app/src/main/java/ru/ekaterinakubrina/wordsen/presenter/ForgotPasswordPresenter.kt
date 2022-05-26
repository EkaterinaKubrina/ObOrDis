package ru.ekaterinakubrina.wordsen.presenter

import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.contracts.ForgotPasswordContract

open class ForgotPasswordPresenter(var forgotPasswordContractView: ForgotPasswordContract.View) :
    ForgotPasswordContract.Presenter {

    override fun sendLetterChangePassword(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    forgotPasswordContractView.successSend(email)
                } else {
                    forgotPasswordContractView.emailNotExist(email)
                }
            }
    }
}