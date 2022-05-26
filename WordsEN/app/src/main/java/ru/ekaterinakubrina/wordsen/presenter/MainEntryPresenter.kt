package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.MainEntryContract
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.model.DictionaryModel
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.notify.NotificationNewTest
import java.text.SimpleDateFormat
import java.util.*

open class MainEntryPresenter(
    var context: Context,
    private var mainEntryContractView: MainEntryContract.View
) : MainEntryContract.Presenter {
    private val userModel = UsersModel(UserDaoImpl(context))
    private val wordModel = WordsModel(WordDaoImpl(context))
    private var dictionaryModel = DictionaryModel(DictionaryDaoImpl(context), wordModel)
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()

    override fun loadData(uid: String) {
        mainEntryContractView.initTextToSpeech()
        mainEntryContractView.setName(mainEntryContractView.getName())
        setWordDay(uid)
        mainEntryContractView.setLevel(getLevelUser(uid))
    }

    override fun setLevel(uid: String, newLevel: Int) {
        mainEntryContractView.restartActivity(uid, newLevel)
        userModel.setLevelLocalAndFirebase(uid, newLevel)
    }

    override fun getLevelUser(uid: String): Int {
        return userModel.getLevelUser(uid)!!
    }

    override fun alreadyKnowWord(uid: String) {
        dictionaryModel.alreadyKnowWord(
            uid,
            dictionaryModel.getWordByDate(uid, currentDate).wordId,
            currentDate - 1
        )
        setWordDay(uid)
    }

    override fun deleteNewAndBadStudiedWords(uid: String) {
        dictionaryModel.deleteNewAndBadStudiedWords(uid)
    }

    open fun setWordDay(uid: String) {
        val levelUser = getLevelUser(uid)
        val lastDate = dictionaryModel.getLastDateAddedWord(uid)
        if (lastDate?.equals(currentDate) == false) {
            var word = dictionaryModel.getWord(uid, levelUser)
            if (word.wordId == 0) {
                mainEntryContractView.newLevel()
                userModel.setLevelLocalAndFirebase(uid, levelUser + 1)
                word = dictionaryModel.getWord(uid, levelUser + 1)
                mainEntryContractView.setLevel(levelUser + 1)
            }
            dictionaryModel.addWordToUser(uid, word)
            mainEntryContractView.setWord(word)

            if (dictionaryModel.getCountNewWord(uid)!! - 1 == 7) {
                NotificationNewTest.showNotification(context)
            }
        } else {
            mainEntryContractView.setWord(dictionaryModel.getWordByDate(uid, currentDate))
        }
    }

}