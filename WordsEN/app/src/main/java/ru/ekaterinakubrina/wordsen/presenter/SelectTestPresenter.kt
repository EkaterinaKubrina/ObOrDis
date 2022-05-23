package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.view.SelectTestContractView

class SelectTestPresenter(var context: Context, var selectTestContractView: SelectTestContractView) {
    private val wordModel = WordsModel(context)

    fun availableTest(uid: String){
        if (wordModel.getCountNewWord(uid)!! - 1 < 7) {
            selectTestContractView.setNotAvailableWeekTest()
        } else {
            selectTestContractView.setAvailableWeekTest()
        }

        if (wordModel.getCountStudiedWord(uid)!! < 14) {
            selectTestContractView.setNotAvailableAllTest()
        } else {
            selectTestContractView.setAvailableAllTest()
        }
    }
}