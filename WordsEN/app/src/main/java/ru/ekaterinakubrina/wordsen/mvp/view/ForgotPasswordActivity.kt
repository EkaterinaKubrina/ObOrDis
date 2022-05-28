package ru.ekaterinakubrina.wordsen.mvp.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.mvp.contracts.ForgotPasswordContract
import ru.ekaterinakubrina.wordsen.mvp.presenter.ForgotPasswordPresenter

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {
    private var forgotPasswordPresenter : ForgotPasswordContract.Presenter = ForgotPasswordPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val buttonSendPassword: Button = findViewById(R.id.buttonSendPassword)
        buttonSendPassword.setOnClickListener {
            forgotPasswordPresenter.clickOnSendNewPasswordButton()
        }
    }

    override fun getEmail(): String {
        val textEmail: EditText = findViewById(R.id.textEmail)
        return textEmail.text.toString()
    }

    override fun successSend(email: String) {
        Toast.makeText(this, "Reset email instructions sent to $email", Toast.LENGTH_LONG).show()
    }

    override fun emailNotExist(email: String) {
        Toast.makeText(this, "$email does not exist", Toast.LENGTH_LONG).show()
    }
}