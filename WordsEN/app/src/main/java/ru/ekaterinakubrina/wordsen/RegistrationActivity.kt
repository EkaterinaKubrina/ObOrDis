package ru.ekaterinakubrina.wordsen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var buttonReg: Button = findViewById(R.id.button)
        buttonReg.setOnClickListener {
        }

    }
}