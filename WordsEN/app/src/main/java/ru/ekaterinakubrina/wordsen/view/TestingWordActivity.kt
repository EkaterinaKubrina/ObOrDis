package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.presenter.WordPresenter

class TestingWordActivity : AppCompatActivity() {
    private val wordPresenter = WordPresenter(WordDaoImpl(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_word)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        val type: String = intent.getSerializableExtra("TEST_TYPE") as String

        val testWord: TextView = findViewById(R.id.testWord)
        val indexCurrentWord: TextView = findViewById(R.id.indexCurrentWord)
        val toAnswer: Button = findViewById(R.id.toAnswer)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        var checkedVar: String? = null
        var indexWord = 0
        var rightAnswers = 0

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                checkedVar = text.toString()
            }
        }

        radioGroup.clearCheck()

        val list: ArrayList<WordDto> = if (type == "all") {
            wordPresenter.getUserStudiedWords(uid)
        } else {
            wordPresenter.getUserNewWords(uid)
        }

        testWord.text = list[indexWord].word
        place(list[indexWord].translate, (1..4).random())
        val str = "${indexWord+1}/${list.size}"
        indexCurrentWord.text = str

        toAnswer.setOnClickListener {
            radioGroup.clearCheck()
            if (checkedVar != null) {
                if (list[indexWord].translate == checkedVar) {
                    Toast.makeText(
                        this@TestingWordActivity,
                        "Верно!",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (type == "week") {
                        wordPresenter.setStatusWord(
                            uid,
                            list[indexWord].wordId,
                            MyDbWordsEN.UsersWords.STUDIED,
                            list[indexWord].status
                        )
                    }
                    rightAnswers++
                } else {
                    Toast.makeText(
                        this@TestingWordActivity,
                        "Не верно :(",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (list[indexWord].status == MyDbWordsEN.UsersWords.BAD_STUDIED) {
                        wordPresenter.deleteWord(uid, list[indexWord].wordId)
                    } else {
                        wordPresenter.setStatusWord(
                            uid,
                            list[indexWord].wordId,
                            MyDbWordsEN.UsersWords.BAD_STUDIED,
                            list[indexWord].status
                        )
                    }

                }
                if (list.size - 1 > indexWord) {
                    indexWord++
                    testWord.text = list[indexWord].word
                    place(list[indexWord].translate, (1..4).random())
                    val str1 = "${indexWord+1}/${list.size}"
                    indexCurrentWord.text = str1
                } else {
                    val intent = Intent(this, ResultTestActivity::class.java)
                    intent.putExtra("ID_USER", uid)
                    intent.putExtra("RESULT", rightAnswers)
                    intent.putExtra("QUESTIONS", list.size)
                    startActivity(intent)
                }
            }
        }


    }

    private fun place(s: String, pos: Int) {
        val answer1: RadioButton = findViewById(R.id.answer1)
        val answer2: RadioButton = findViewById(R.id.answer2)
        val answer3: RadioButton = findViewById(R.id.answer3)
        val answer4: RadioButton = findViewById(R.id.answer4)

        val randomTranslates = wordPresenter.getTranslateForTest(s)

        when (pos) {
            1 -> {
                answer1.text = s
                answer2.text = randomTranslates[0]
                answer3.text = randomTranslates[1]
                answer4.text = randomTranslates[2]
            }
            2 -> {
                answer2.text = s
                answer1.text = randomTranslates[0]
                answer3.text = randomTranslates[1]
                answer4.text = randomTranslates[2]
            }
            3 -> {
                answer3.text = s
                answer2.text = randomTranslates[0]
                answer1.text = randomTranslates[1]
                answer4.text = randomTranslates[2]
            }
            4 -> {
                answer4.text = s
                answer2.text = randomTranslates[0]
                answer3.text = randomTranslates[1]
                answer1.text = randomTranslates[2]
            }
        }
    }

}