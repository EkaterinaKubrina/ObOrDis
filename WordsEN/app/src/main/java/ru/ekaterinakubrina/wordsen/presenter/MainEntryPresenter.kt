package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.notify.NotificationNewTest
import ru.ekaterinakubrina.wordsen.view.MainEntryContractView
import java.text.SimpleDateFormat
import java.util.*

class MainEntryPresenter(
    var context: Context,
    private var mainEntryContractView: MainEntryContractView
) {
    private val userModel = UsersModel(context)
    private val wordModel = WordsModel(context)
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()
    private var levelUser: Int? = null

    fun loadData(uid: String) {
        levelUser = userModel.getLevelUser(uid)!!
        mainEntryContractView.initTextToSpeech()
        mainEntryContractView.setName(userModel.getNameUser(uid)!!)
        setWordDay(uid)
        mainEntryContractView.setLevel(levelUser!!)
    }

    fun setLevel(uid: String): Int {
        return userModel.setLevel(uid, levelUser!!)
    }

    fun getLevelUser(uid: String): Int? {
        return userModel.getLevelUser(uid)
    }

    fun alreadyKnowWord(uid: String) {
        wordModel.alreadyKnowWord(
            uid,
            wordModel.getWordByDate(uid, currentDate).wordId,
            currentDate - 1,
            MyDbWordsEN.Dictionary.STUDIED
        )
        setWordDay(uid)
    }

    fun deleteNewAndBadStudiedWords(uid: String) {
        wordModel.deleteNewAndBadStudiedWords(uid)
    }

    private fun setWordDay(uid: String) {
        val lastDate = wordModel.getLastDateAddedWord(uid)
        if (lastDate?.equals(currentDate) == false) {
            var word = wordModel.getWord(uid, levelUser!!)
            if (word.wordId == 0) {
                mainEntryContractView.newLevel()
                userModel.setLevel(uid, levelUser!! + 1)
                word = wordModel.getWord(uid, levelUser!! + 1)
                mainEntryContractView.setLevel(levelUser!! + 1)
            }
            wordModel.addWordToUser(uid, word)
            mainEntryContractView.setWord(word)

            if (wordModel.getCountNewWord(uid)!! - 1 == 7) {
                NotificationNewTest.showNotification(context)
            }
        } else {
            mainEntryContractView.setWord(wordModel.getWordByDate(uid, currentDate))
        }
    }

}