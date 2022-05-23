package ru.ekaterinakubrina.wordsen.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R

class SuccessRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_registration)

        val text: TextView = findViewById(R.id.textSuccess)
        val buttonBack: Button = findViewById(R.id.button2)

        val flagSuccessRegistration: Boolean
        val nameUser: String? = intent.getSerializableExtra("NAME_USER") as String?

        if (nameUser != null) {
            text.text = nameUser.plus(", регистрация прошла успешно!")
            intent.removeExtra("NAME_USER")
            flagSuccessRegistration = true
        } else {
            flagSuccessRegistration = false
            buttonBack.text = "Вернуться назад"
            text.text = "Не удалось зарегистрироваться :("
        }

        buttonBack.setOnClickListener {
            if (flagSuccessRegistration) {
                val intent = intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = intent.setClass(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }

}