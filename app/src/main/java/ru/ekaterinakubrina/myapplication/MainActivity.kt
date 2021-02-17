package ru.ekaterinakubrina.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mHelloTextView = findViewById<TextView>(R.id.textView2)
        val nameDog = findViewById<TextView>(R.id.editTextTextPersonName)
        val imageButton = findViewById<ImageButton>(R.id.imageButton2)
        imageButton.setOnClickListener {
            if (nameDog.text.isEmpty()) {
                mHelloTextView.text = " Hello Dog! ";
            } else {
                mHelloTextView.text = "Hello, " + nameDog.text + "!"
            }
        }
    }
}