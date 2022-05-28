package ru.ekaterinakubrina.wordsen.mvp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.mvp.contracts.RegistrationContract
import ru.ekaterinakubrina.wordsen.mvp.presenter.RegistrationPresenter
import java.util.*


class RegistrationActivity : AppCompatActivity(), RegistrationContract.View {
    private val registrationPresenter : RegistrationContract.Presenter = RegistrationPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val buttonRegistration: Button = findViewById(R.id.buttonRegistration)
        buttonRegistration.setOnClickListener {
            registrationPresenter.clickOnRegistrationButton()
        }
    }

    override fun getName(): String {
        val name: EditText = findViewById(R.id.textPersonName)
        return name.text.toString()
    }

    override fun getEmail(): String {
        val email: EditText = findViewById(R.id.textEmailAddress)
        return email.text.toString()
    }

    override fun getPassword(): String {
        val password: EditText = findViewById(R.id.textPassword)
        return password.text.toString()
    }

    override fun getPassword2(): String {
        val password2: EditText = findViewById(R.id.textPassword2)
        return password2.text.toString()
    }

    override fun successRegistration() {
        val name: EditText = findViewById(R.id.textPersonName)
        val intent = Intent(this, SuccessRegistrationActivity()::class.java)
        intent.putExtra(
            "NAME_USER",
            name.text.toString().trim().capitalize(Locale.ROOT)
        )
        startActivity(intent)
    }

    override fun setTextError(text: String) {
        val textError: TextView = findViewById(R.id.textError)
        textError.text = text
    }

    override fun underlineRedName() {
        val name: EditText = findViewById(R.id.textPersonName)
        name.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

    override fun underlineRedPassword() {
        val password: EditText = findViewById(R.id.textPassword)
        password.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

    override fun underlineRedRepeatPassword() {
        val password2: EditText = findViewById(R.id.textPassword2)
        password2.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

    override fun underlineRedEmail() {
        val email: EditText = findViewById(R.id.textEmailAddress)
        email.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

}