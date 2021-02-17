package ru.ekaterinakubrina.myapplication2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private var counter : Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val button_counter = findViewById<Button>(R.id.button_counter)
        val textView = findViewById<TextView>(R.id.textView)
        val textView8 = findViewById<TextView>(R.id.textView8)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val textView6 = findViewById<TextView>(R.id.textView6)
        val textView7 = findViewById<TextView>(R.id.textView7)

        button.setOnClickListener {
            textView.text = "Hello!:)"
            textView2.text = "Hi!"
            textView3.text = "Hello!"
            textView4.text = "Hello!:)"
            textView5.text = "Hello!:)"
            textView6.text = "Good morning?"
            textView7.text = "Good evening?"
            textView8.text = "Hey!"
        }

        button_counter.setOnClickListener {
            textView5.text = "Я насчиталa ${++counter} кликов"
        }
    }
}