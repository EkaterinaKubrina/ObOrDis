package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonEntry: Button = findViewById(R.id.buttonEntryOnMain)
        buttonEntry.setOnClickListener {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
        }

        val buttonReg: Button = findViewById(R.id.buttonRegistrationOnMain)
        buttonReg.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}