package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.view.DictionaryContractView

class DictionaryPresenter(
    var context: Context,
    var dictionaryContractView: DictionaryContractView
) {
    private val userModel = UsersModel(context)
    private val wordModel = WordsModel(context)

    fun getDictionary(uid: String) {
        dictionaryContractView.showWords(userModel.getDictionary(uid))
    }

    fun addUsersWord(uid: String, word: String, translate: String, transcription: String) {
        if (word == "") {
            dictionaryContractView.showText("Поля не могут быть пустыми")
            dictionaryContractView.underlineRedWord()
        } else if (translate == "") {
            dictionaryContractView.showText("Поля не могут быть пустыми")
            dictionaryContractView.underlineRedTranslate()
        } else {
            dictionaryContractView.removeUnderline()
            val wordDto = WordDto(0, word, transcription, translate)
            if (wordModel.addUsersWord(wordDto, userModel.getLevelUser(uid)!!, uid)) {
                dictionaryContractView.clearTextView()
                dictionaryContractView.showText("Слово добавлено")
            } else {
                dictionaryContractView.showText("Не получилось добавить слово. Попробуйте позже")
            }
        }


    }

}