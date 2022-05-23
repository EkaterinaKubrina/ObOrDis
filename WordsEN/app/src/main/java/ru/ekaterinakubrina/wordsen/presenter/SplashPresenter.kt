package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.view.SplashContractView

class SplashPresenter(var context: Context, var splashContractView: SplashContractView) {
    private val userModel = UsersModel(context)

    fun checkAuth() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val user = auth.currentUser!!.uid.let { userModel.getUser(it) }
            if (user != null) {
                splashContractView.authorized(user.userId)
            }
        } else {
            splashContractView.notAuthorized()
        }
    }

}