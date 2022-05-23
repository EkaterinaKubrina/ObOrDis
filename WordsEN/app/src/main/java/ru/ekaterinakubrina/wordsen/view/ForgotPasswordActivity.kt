package ru.ekaterinakubrina.wordsen.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.presenter.ForgotPasswordPresenter

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContractView {
    private var forgotPasswordPresenter = ForgotPasswordPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val buttonSendPassword: Button = findViewById(R.id.buttonSendPassword)
        buttonSendPassword.setOnClickListener {
            val textEmail: EditText = findViewById(R.id.textEmail)
            forgotPasswordPresenter.sendLetterChangePassword(textEmail.text.toString())
        }
    }

    override fun successSend(email: String) {
        Toast.makeText(this, "Reset email instructions sent to $email", Toast.LENGTH_LONG).show()
    }

    override fun emailNotExist(email: String) {
        Toast.makeText(this, "$email does not exist", Toast.LENGTH_LONG).show()
    }
}