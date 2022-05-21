package ru.ekaterinakubrina.wordsen.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.R


class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val textEmail: EditText = findViewById(R.id.textEmail)
        val buttonSendPassword: Button = findViewById(R.id.buttonSendPassword)

        buttonSendPassword.setOnClickListener{
            val auth = FirebaseAuth.getInstance()
            val email = textEmail.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Reset email instructions sent to $email",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(this, "$email does not exist", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}