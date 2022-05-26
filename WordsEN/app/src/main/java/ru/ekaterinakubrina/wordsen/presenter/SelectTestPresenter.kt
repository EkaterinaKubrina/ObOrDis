package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.SelectTestContract
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.model.DictionaryModel
import ru.ekaterinakubrina.wordsen.model.WordsModel

open class SelectTestPresenter(
    var context: Context,
    var selectTestContractView: SelectTestContract.View
) : SelectTestContract.Presenter {
    private val wordModel = WordsModel(WordDaoImpl(context))
    private var dictionaryModel = DictionaryModel(DictionaryDaoImpl(context), wordModel)

    override fun availableTest(uid: String) {
        if (dictionaryModel.getCountNewWord(uid)!! - 1 < 7) {
            selectTestContractView.setNotAvailableWeekTest()
        } else {
            selectTestContractView.setAvailableWeekTest()
        }

        if (dictionaryModel.getCountStudiedWord(uid)!! < 14) {
            selectTestContractView.setNotAvailableAllTest()
        } else {
            selectTestContractView.setAvailableAllTest()
        }
    }
}