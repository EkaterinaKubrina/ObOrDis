package ru.ekaterinakubrina.wordsen.mvp.presenter

import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.mvp.contracts.ForgotPasswordContract

open class ForgotPasswordPresenter(var forgotPasswordContractView: ForgotPasswordContract.View) :
    ForgotPasswordContract.Presenter {

    override fun clickOnSendNewPasswordButton() {
        val email = forgotPasswordContractView.getEmail()
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