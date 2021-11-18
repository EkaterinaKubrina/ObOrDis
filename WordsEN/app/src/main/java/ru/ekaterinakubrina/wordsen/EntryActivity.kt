package ru.ekaterinakubrina.wordsen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        var buttonEntry: Button = findViewById(R.id.buttonEntry)
        buttonEntry.setOnClickListener {
        }
    }
}