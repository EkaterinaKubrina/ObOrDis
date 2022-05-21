package ru.ekaterinakubrina.wordsen.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter
import java.util.*


class RegistrationActivity : AppCompatActivity() {
    private val userPresenter = UserPresenter(UserDaoImpl(this))

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val name: EditText = findViewById(R.id.textPersonName)
            val email: EditText = findViewById(R.id.textEmailAddress)
            val password: EditText = findViewById(R.id.textPassword)
            val password2: EditText = findViewById(R.id.textPassword2)
            val textError: TextView = findViewById(R.id.error2)

            val intent =
                Intent(this@RegistrationActivity, SuccessRegistrationActivity()::class.java)


            if (password.text.toString() == password2.text.toString()) {
                if (userPresenter.checkName(name.text.toString().trim())) {
                    if (userPresenter.checkEmail(email.text.toString().trim())) {
                        if (userPresenter.checkPassword(password.text.toString())) {
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(
                                    email.text.toString(),
                                    password.text.toString()
                                )
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val id: String =
                                            FirebaseAuth.getInstance().currentUser?.uid!!
                                        userPresenter.addUser(
                                            id,
                                            name.text.toString().trim().capitalize(Locale.ROOT),
                                            email.text.toString(),
                                            password.text.toString()
                                        )
                                        intent.putExtra(
                                            "NAME_USER",
                                            name.text.toString().trim().capitalize(Locale.ROOT)
                                        )
                                        startActivity(intent)
                                    } else {
                                        textError.text = task.exception?.message
                                    }
                                }

                        } else {
                            textError.text =
                                "Некорректный пароль (содержит менее 6 знаков или кириллицу)"
                            password.backgroundTintList =
                                AppCompatResources.getColorStateList(this, R.color.myRed)
                        }
                    } else {
                        textError.text = "Некорректный email"
                        email.backgroundTintList =
                            AppCompatResources.getColorStateList(this, R.color.myRed)
                    }
                } else {
                    textError.text = "Некорректное имя пользователя"
                    name.backgroundTintList =
                        AppCompatResources.getColorStateList(this, R.color.myRed)
                }
            } else {
                textError.text = "Введены разные пароли"
                password2.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.myRed)
            }
        }

    }


}