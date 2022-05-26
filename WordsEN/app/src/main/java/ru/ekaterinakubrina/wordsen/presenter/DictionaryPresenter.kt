package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.DictionaryContract
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.model.DictionaryModel
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel

open class DictionaryPresenter(
    var context: Context,
    var dictionaryContractView: DictionaryContract.View
) : DictionaryContract.Presenter {
    var userModel = UsersModel(UserDaoImpl(context))
    var wordModel = WordsModel(WordDaoImpl(context))
    var dictionaryModel = DictionaryModel(DictionaryDaoImpl(context), wordModel)

    override fun getDictionary(uid: String) {
        dictionaryContractView.showWords(userModel.getDictionary(uid))
    }

    override fun addUsersWord(uid: String, word: String, translate: String, transcription: String) {
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
            val wordDto = WordDto(0, word, transcription, translate)
            if (dictionaryModel.addUsersWord(wordDto, userModel.getLevelUser(uid)!!, uid)) {
                dictionaryContractView.clearTextView()
                dictionaryContractView.showText("Слово добавлено")
            } else {
                dictionaryContractView.showText("Не получилось добавить слово. Попробуйте позже")
            }
        }
    }

}