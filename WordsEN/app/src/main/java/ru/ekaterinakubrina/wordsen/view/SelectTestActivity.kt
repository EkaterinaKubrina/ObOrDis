package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.presenter.WordPresenter

class SelectTestActivity : AppCompatActivity() {
    private val wordPresenter = WordPresenter(WordDaoImpl(this))

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_test)

        val uid: String = intent.getSerializableExtra("ID_USER") as String

        val startWeekTest: Button = findViewById(R.id.startWeekTest)
        val startAllTest: Button = findViewById(R.id.startAllTest)

        if (wordPresenter.getCountNewWord(uid)!!-1 < 7) {
            startWeekTest.isEnabled = false
            startWeekTest.setTextColor(getColor(R.color.darkpurpl))
        } else {
            startWeekTest.isEnabled = true
            startWeekTest.setTextColor(getColor(R.color.myfon))
        }

        if (wordPresenter.getCountStudiedWord(uid)!! < 14) {
            startAllTest.isEnabled = false
            startAllTest.setTextColor(getColor(R.color.darkpurpl))
        } else {
            startAllTest.isEnabled = true
            startAllTest.setTextColor(getColor(R.color.myfon))
        }

        startWeekTest.setOnClickListener {
            val intent = Intent(this, TestingWordActivity::class.java)
            intent.putExtra("ID_USER", uid)
            intent.putExtra("TEST_TYPE", "week")
            startActivity(intent)
        }


        startAllTest.setOnClickListener {
            val intent = Intent(this, TestingWordActivity::class.java)
            intent.putExtra("ID_USER", uid)
            intent.putExtra("TEST_TYPE", "all")
            startActivity(intent)
        }


    }
}