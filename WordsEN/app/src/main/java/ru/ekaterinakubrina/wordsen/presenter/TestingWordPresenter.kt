package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.view.TestingWordContractView

class TestingWordPresenter(
    var context: Context,
    var testingWordContractView: TestingWordContractView
) {
    private val wordModel = WordsModel(context)
    private var type = ""
    private var uid = ""
    private var rightAnswers = 0
    private var indexWord = 0
    private lateinit var list: ArrayList<WordDto>

    fun startTest(uid: String, type: String) {
        this.type = type
        this.uid = uid
        list = if (type == "all") {
            wordModel.getUserStudiedWords(uid)
        } else {
            wordModel.getUserNewWords(uid)
        }
        testingWordContractView.nextWord(
            list[indexWord].word,
            list[indexWord].translate,
            indexWord + 1,
            list.size
        )
    }


    fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        return wordModel.getTranslateForTest(rightTranslate)
    }

    fun checkAnswer(checkedVar: String) {
        if (list[indexWord].translate == checkedVar) {
            testingWordContractView.rightAnswer()
            if (type == "week") {
                wordModel.setStatusWord(
                    uid,
                    list[indexWord].wordId,
                    MyDbWordsEN.Dictionary.STUDIED,
                    list[indexWord].status
                )
            }
            rightAnswers++
        } else {
            testingWordContractView.wrongAnswer()
            if (list[indexWord].status == MyDbWordsEN.Dictionary.BAD_STUDIED) {
                wordModel.deleteWord(uid, list[indexWord].wordId)
            } else {
                wordModel.setStatusWord(
                    uid,
                    list[indexWord].wordId,
                    MyDbWordsEN.Dictionary.BAD_STUDIED,
                    list[indexWord].status
                )
            }
        }
        if (list.size - 1 > indexWord) {
            indexWord++
            testingWordContractView.nextWord(
                list[indexWord].word,
                list[indexWord].translate,
                indexWord + 1,
                list.size
            )
        } else {
            testingWordContractView.nextActivity(uid, rightAnswers, list.size)
        }

    }
}