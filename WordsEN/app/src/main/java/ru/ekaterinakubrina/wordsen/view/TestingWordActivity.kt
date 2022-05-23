package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.presenter.TestingWordPresenter

class TestingWordActivity : AppCompatActivity(), TestingWordContractView {
    private val testPresenter = TestingWordPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_word)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        val type: String = intent.getSerializableExtra("TEST_TYPE") as String
        testPresenter.startTest(uid, type)

        var checkedVar = ""
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                checkedVar = text.toString()
            }
        }

        val toAnswer: Button = findViewById(R.id.toAnswer)
        toAnswer.setOnClickListener {
            radioGroup.clearCheck()
            testPresenter.checkAnswer(checkedVar)
        }

    }

    override fun rightAnswer() {
        Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show()
    }

    override fun wrongAnswer() {
        Toast.makeText(this, "Не верно :(", Toast.LENGTH_SHORT).show()
    }

    override fun nextActivity(uid: String, rightAnswers: Int, listSize: Int) {
        val intent = Intent(this, ResultTestActivity::class.java)
        intent.putExtra("ID_USER", uid)
        intent.putExtra("RESULT", rightAnswers)
        intent.putExtra("QUESTIONS", listSize)
        startActivity(intent)
    }

    override fun nextWord(word: String, translate: String, nowIndex: Int, size: Int) {
        val testWord: TextView = findViewById(R.id.testWord)
        val indexCurrentWord: TextView = findViewById(R.id.indexCurrentWord)
        testWord.text = word
        place(translate, (1..4).random())
        val str1 = "${nowIndex}/${size}"
        indexCurrentWord.text = str1
    }

    private fun place(s: String, pos: Int) {
        val answer1: RadioButton = findViewById(R.id.answer1)
        val answer2: RadioButton = findViewById(R.id.answer2)
        val answer3: RadioButton = findViewById(R.id.answer3)
        val answer4: RadioButton = findViewById(R.id.answer4)

        val randomTranslates = testPresenter.getTranslateForTest(s)

        when (pos) {
            1 -> {
                answer1.text = s
                answer2.text = randomTranslates[0]
                answer3.text = randomTranslates[1]
                answer4.text = randomTranslates[2]
            }
            2 -> {
                answer1.text = randomTranslates[0]
                answer2.text = s
                answer3.text = randomTranslates[1]
                answer4.text = randomTranslates[2]
            }
            3 -> {
                answer1.text = randomTranslates[1]
                answer2.text = randomTranslates[0]
                answer3.text = s
                answer4.text = randomTranslates[2]
            }
            4 -> {
                answer1.text = randomTranslates[2]
                answer2.text = randomTranslates[0]
                answer3.text = randomTranslates[1]
                answer4.text = s
            }
        }
    }

}