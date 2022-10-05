package ru.ekaterinakubrina.oneworday.mvp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.oneworday.R
import ru.ekaterinakubrina.oneworday.mvp.contracts.TestContract
import ru.ekaterinakubrina.oneworday.mvp.presenter.TestPresenter

class TestActivity : AppCompatActivity(), TestContract.View {
    private val testPresenter : TestContract.Presenter = TestPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val language: Int = intent.getSerializableExtra("LANGUAGE") as Int
        testPresenter.initTest(language)

        val correct1: RadioButton = findViewById(R.id.correct1)
        val correct2: RadioButton = findViewById(R.id.correct2)
        val correct3: RadioButton = findViewById(R.id.correct3)
        val correct4: RadioButton = findViewById(R.id.correct4)
        val correct5: RadioButton = findViewById(R.id.correct5)
        val correct6: RadioButton = findViewById(R.id.correct6)
        val correct7: RadioButton = findViewById(R.id.correct7)
        val correct8: RadioButton = findViewById(R.id.correct8)
        val correct9: RadioButton = findViewById(R.id.correct9)
        val correct10: RadioButton = findViewById(R.id.correct10)

        var count = 0
        val buttonEnd: Button = findViewById(R.id.buttonTheEnd)
        buttonEnd.setOnClickListener {
            if (correct1.isChecked) {
                count++
            }
            if (correct2.isChecked) {
                count++
            }
            if (correct3.isChecked) {
                count += 2
            }
            if (correct4.isChecked) {
                count += 2
            }
            if (correct5.isChecked) {
                count += 3
            }
            if (correct6.isChecked) {
                count += 3
            }
            if (correct7.isChecked) {
                count += 4
            }
            if (correct8.isChecked) {
                count += 4
            }
            if (correct9.isChecked) {
                count += 5
            }
            if (correct10.isChecked) {
                count += 5
            }
            count /= 2
            testPresenter.clickOnEndButton(count, intent.getSerializableExtra("ID_USER") as String)
        }
    }

    override fun nextActivity(id: String, level: Int) {
        val language: Int = intent.getSerializableExtra("LANGUAGE") as Int
        val intent = Intent(this, MainEntryActivity::class.java)
        intent.putExtra("ID_USER", id)
        intent.putExtra("LEVEL_USER", level)
        intent.putExtra("LANGUAGE", language)
        startActivity(intent)
    }

    override fun setWords(list: List<String>) {
        val word1: TextView = findViewById(R.id.word1)
        word1.text = list[0]
        val word2: TextView = findViewById(R.id.word2)
        word2.text = list[1]
        val word3: TextView = findViewById(R.id.word3)
        word3.text = list[2]
        val word4: TextView = findViewById(R.id.word4)
        word4.text = list[3]
        val word5: TextView = findViewById(R.id.word5)
        word5.text = list[4]
        val word6: TextView = findViewById(R.id.word6)
        word6.text = list[5]
        val word7: TextView = findViewById(R.id.word7)
        word7.text = list[6]
        val word8: TextView = findViewById(R.id.word8)
        word8.text = list[7]
        val word9: TextView = findViewById(R.id.word9)
        word9.text = list[8]
        val word10: TextView = findViewById(R.id.word10)
        word10.text = list[9]
    }
}