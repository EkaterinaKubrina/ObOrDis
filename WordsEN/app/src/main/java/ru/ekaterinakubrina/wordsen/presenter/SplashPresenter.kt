package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.contracts.SplashContract
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.model.UsersModel

open class SplashPresenter(var context: Context, var splashContractView: SplashContract.View) :
    SplashContract.Presenter {
    private val userModel = UsersModel(UserDaoImpl(context))

    override fun checkAuth() {
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