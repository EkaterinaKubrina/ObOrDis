package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.oneworday.mvp.contracts.RegistrationContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository
import java.util.*

open class RegistrationPresenter(
    var context: Context,
    private var registrationContractView: RegistrationContract.View
) : RegistrationContract.Presenter {
    private val repository: RegistrationContract.Model = Repository.getRepository(context)

    override fun clickOnRegistrationButton() {
        registrationContractView.clearUnderline()
        val name = registrationContractView.getName()
        val email = registrationContractView.getEmail()
        val password = registrationContractView.getPassword()
        val password2 = registrationContractView.getPassword2()

        if (password == password2) {
            if (checkName(name.trim())) {
                if (checkEmail(email.trim())) {
                    if (checkPassword(password)) {
                        registrationContractView.setMyProgressBarVisibility(true)
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val id: String =
                                        FirebaseAuth.getInstance().currentUser?.uid!!
                                    repository.addUserLocalAndFirebase(
                                        id,
                                        name.trim().capitalize(Locale.ROOT),
                                        email,
                                        password
                                    )
                                    FirebaseAuth.getInstance().signOut()
                                    registrationContractView.successRegistration()
                                } else {
                                    registrationContractView.setMyProgressBarVisibility(false)
                                    task.exception?.message?.let {
                                        registrationContractView.setTextError(it)
                                    }
                                }
                            }

                    } else {
                        registrationContractView.setTextError("Некорректный пароль (содержит менее 6 знаков или кириллицу)")
                        registrationContractView.underlineRedPassword()
                    }
                } else {
                    registrationContractView.setTextError("Некорректный email")
                    registrationContractView.underlineRedEmail()
                }
            } else {
                registrationContractView.setTextError("Некорректное имя пользователя")
                registrationContractView.underlineRedName()
            }
        } else {
            registrationContractView.setTextError("Введены разные пароли")
            registrationContractView.underlineRedRepeatPassword()
        }
    }

    private fun checkEmail(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    private fun checkPassword(str: String): Boolean {
        if (str.length < 6) {
            return false
        } else if (str.contains("^[а-яА-я\\s]".toRegex())) {
            return false
        }
        return true
    }

    private fun checkName(str: String): Boolean {
        if (str.length < 2) {
            return false
        } else if (str.contains("[^a-zA-ZА-Яа-я]".toRegex())) {
            return false
        }
        return true
    }
}