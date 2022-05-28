package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.mvp.contracts.MainEntryContract
import ru.ekaterinakubrina.wordsen.mvp.model.Repository
import ru.ekaterinakubrina.wordsen.notify.NotificationNewTest
import java.text.SimpleDateFormat
import java.util.*

open class MainEntryPresenter(
    var context: Context,
    private var mainEntryContractView: MainEntryContract.View
) : MainEntryContract.Presenter {
    private val repository: Repository = Repository.getRepository(context)
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()

    override fun loadData(uid: String) {
        mainEntryContractView.initTextToSpeech()
        val user = repository.getUser(uid)
        setWordDay(uid)
        if (user != null) {
            mainEntryContractView.setName(user.name)
            mainEntryContractView.setLevel(user.level)
        }
    }

    override fun setLevel(uid: String, newLevel: Int) {
        mainEntryContractView.restartActivity(uid, newLevel)
        repository.setLevelLocalAndFirebase(uid, newLevel)
    }

    override fun getLevelUser(uid: String): Int? {
        return repository.getLevelUser(uid)
    }

    override fun clickOnAlreadyKnowWordButton(uid: String) {
        repository.alreadyKnowWord(
            uid,
            repository.getWordByDate(uid, currentDate).wordId,
            currentDate - 1
        )
        setWordDay(uid)
    }

    override fun deleteNewAndBadStudiedWords(uid: String) {
        repository.deleteNewAndBadStudiedWords(uid)
    }

    open fun setWordDay(uid: String) {
        val levelUser = getLevelUser(uid)
        val lastDate = repository.getLastDateAddedWord(uid)
        if (lastDate?.equals(currentDate) == false) {
            var word = levelUser?.let { repository.getWord(uid, it) }
            if (word != null) {
                if (word.wordId == 0) {
                    mainEntryContractView.newLevel()
                    repository.setLevelLocalAndFirebase(uid, levelUser!! + 1)
                    word = repository.getWord(uid, levelUser + 1)
                    mainEntryContractView.setLevel(levelUser + 1)
                }
                repository.addWordToUser(uid, word)
                mainEntryContractView.setWord(word)
            }
            if (repository.getCountNewWord(uid)!! - 1 == 7) {
                NotificationNewTest.showNotification(context)
            }
        } else {
            mainEntryContractView.setWord(repository.getWordByDate(uid, currentDate))
        }
    }

}