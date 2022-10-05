package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.dto.WordDto
import ru.ekaterinakubrina.oneworday.mvp.contracts.TestingWordContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class TestingWordPresenter(
    var context: Context,
    private var testingWordContractView: TestingWordContract.View
) : TestingWordContract.Presenter {
    private val repository: TestingWordContract.Model = Repository.getRepository(context)
    private var type = ""
    private var uid = ""
    private var rightAnswers = 0
    private var indexWord = 0
    private lateinit var list: ArrayList<WordDto>

    override fun startTest(uid: String, type: String) {
        this.type = type
        this.uid = uid
        list = if (type == "all") {
            repository.getUserStudiedWords(uid)
        } else {
            repository.getUserNewWords(uid)
        }
        if (list.size > 0) {
            testingWordContractView.nextWord(
                list[indexWord].word,
                list[indexWord].translate,
                indexWord + 1,
                list.size
            )
        }
    }


    override fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        return repository.getTranslateForTest(rightTranslate)
    }

    override fun checkAnswer(checkedVar: String) {
        if (list[indexWord].translate == checkedVar) {
            testingWordContractView.rightAnswer()
            if (type == "week") {
                repository.setStatusWord(
                    uid,
                    list[indexWord].wordId,
                    MyDbWordsEN.Status.STUDIED,
                    list[indexWord].status
                )
            }
            rightAnswers++
        } else {
            testingWordContractView.wrongAnswer()
            if (list[indexWord].status == MyDbWordsEN.Status.BAD_STUDIED) {
                repository.deleteWordFromUser(uid, list[indexWord].wordId)
            } else {
                repository.setStatusWord(
                    uid,
                    list[indexWord].wordId,
                    MyDbWordsEN.Status.BAD_STUDIED,
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