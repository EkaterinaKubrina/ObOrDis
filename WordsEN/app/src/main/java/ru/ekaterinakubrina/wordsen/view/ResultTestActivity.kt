package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter

class ResultTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_test)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        val resCount: Int = intent.getSerializableExtra("RESULT") as Int
        val questionCount: Int = intent.getSerializableExtra("QUESTIONS") as Int

        val resultText: TextView = findViewById(R.id.resultText)
        val buttonOk: Button = findViewById(R.id.buttonOk)


        val str: String = if (resCount >= (questionCount / 2)) {
            "Твой результат - $resCount/$questionCount. Молодец!"
        } else {
            "Твой результат - $resCount/$questionCount. Нужно поднажать!"
        }

        resultText.text = str

        buttonOk.setOnClickListener {
            val intent = Intent(this, MainEntryActivity::class.java)
            intent.putExtra("ID_USER", uid)
            startActivity(intent)
        }

    }
}