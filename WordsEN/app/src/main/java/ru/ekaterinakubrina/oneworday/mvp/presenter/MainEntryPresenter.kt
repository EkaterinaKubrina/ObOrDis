package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import android.widget.PopupMenu
import ru.ekaterinakubrina.oneworday.mvp.contracts.MainEntryContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository
import ru.ekaterinakubrina.oneworday.notify.NotificationNewTest
import ru.ekaterinakubrina.oneworday.notify.NotifyService
import java.text.SimpleDateFormat
import java.util.*

open class MainEntryPresenter(
    var context: Context,
    private var mainEntryContractView: MainEntryContract.View
) : MainEntryContract.Presenter {
    private val repository: MainEntryContract.Model = Repository.getRepository(context)
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()

    override fun loadData(uid: String) {
        val user = repository.getUser(uid)
        if (user != null) {
            mainEntryContractView.initTextToSpeech(user.language)
            if (!NotifyService.on) {
                mainEntryContractView.setAlarm(user)
            }
            setWordDay(uid, user.language)
            mainEntryContractView.setName(user.name)
            mainEntryContractView.setLevel(user.level)
        }
    }

    override fun enableLanguage(uid: String, pop: PopupMenu) {
        mainEntryContractView.changeLanguageEnable(repository.getLanguageUser(uid)!!, pop)
    }

    override fun setLevel(uid: String, newLevel: Int) {
        repository.deleteNewAndBadStudiedWords(uid)
        repository.setLevelLocalAndFirebase(uid, newLevel)
        setWordDay(uid, repository.getLanguageUser(uid)!!)
        mainEntryContractView.setLevel(newLevel)
    }

    override fun setLanguage(uid: String, newLanguage: Int) {
        repository.setUserLanguage(uid, newLanguage)
        loadData(uid)
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
        setWordDay(uid, repository.getLanguageUser(uid)!!)
    }

    open fun setWordDay(uid: String, language: Int) {
        val levelUser = repository.getLevelUser(uid)
        val lastDate = repository.getLastDateAddedWord(uid)
        if (lastDate?.equals(currentDate) == false) {
            var word = levelUser?.let { repository.getWord(uid, it, language) }
            if (word != null) {
                if (word.wordId == 0) {
                    repository.setLevelLocalAndFirebase(uid, levelUser!! + 1)
                    word = repository.getWord(uid, levelUser + 1, language)
                    mainEntryContractView.setLevel(levelUser + 1)
                    mainEntryContractView.newLevel()
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