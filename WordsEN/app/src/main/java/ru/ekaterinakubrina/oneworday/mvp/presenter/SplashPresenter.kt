package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.oneworday.mvp.contracts.SplashContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class SplashPresenter(var context: Context, var splashContractView: SplashContract.View) :
    SplashContract.Presenter {
    private val repository: SplashContract.Model = Repository.getRepository(context)

    override fun checkAuth() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val user = auth.currentUser!!.uid.let { repository.getUser(it) }
            if (user != null) {
                splashContractView.authorized(user.userId)
            }
            else{
                auth.signOut()
                splashContractView.notAuthorized()
            }
        } else {
            splashContractView.notAuthorized()
        }
    }

}