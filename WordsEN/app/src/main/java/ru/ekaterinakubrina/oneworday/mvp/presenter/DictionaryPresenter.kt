package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.dto.WordDto
import ru.ekaterinakubrina.oneworday.mvp.contracts.DictionaryContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class DictionaryPresenter(
    var context: Context,
    private var dictionaryContractView: DictionaryContract.View
) : DictionaryContract.Presenter {
    private val repository: DictionaryContract.Model = Repository.getRepository(context)

    override fun getDictionary(uid: String) {
        dictionaryContractView.showWords(repository.getDictionary(uid)!!)
    }

    override fun clickOnAddWordButton(
        uid: String,
        word: String,
        translate: String,
        transcription: String
    ) {
        if (word == "") {
            dictionaryContractView.showText("Поля не могут быть пустыми")
            dictionaryContractView.underlineRedWord()
        } else if (word.contains("[А-Яа-я0-9._%+=/?]".toRegex())) {
            dictionaryContractView.showText("Слово не может содержать цифры, знаки и русские буквы")
            dictionaryContractView.underlineRedWord()
        } else if (translate == "") {
            dictionaryContractView.showText("Поля не могут быть пустыми")
            dictionaryContractView.underlineRedTranslate()
        } else if (translate.contains("[^А-Яа-я-]".toRegex())) {
            dictionaryContractView.showText("Перевод должен быть на русском языке")
            dictionaryContractView.underlineRedTranslate()
        } else {
            dictionaryContractView.removeUnderline()
            val wordDto =
                WordDto(0, word, transcription, translate, repository.getLanguageUser(uid)!!)
            if (repository.addUsersWord(wordDto, repository.getLevelUser(uid)!!, uid)!!) {
                dictionaryContractView.clearTextView()
                dictionaryContractView.showText("Слово добавлено")
            } else {
                dictionaryContractView.showText("Не получилось добавить слово. Попробуйте позже")
            }
        }
    }

}