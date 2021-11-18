package ru.ekaterinakubrina.wordsen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonEntry: Button = findViewById(R.id.entry)
        buttonEntry.setOnClickListener {
            val intent1 = Intent(this@MainActivity, EntryActivity::class.java)
            startActivity(intent1)
        }

        var buttonReg: Button = findViewById(R.id.registration)
        buttonReg.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }
}