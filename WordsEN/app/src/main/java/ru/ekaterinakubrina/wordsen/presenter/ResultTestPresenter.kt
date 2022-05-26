package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.ResultTestContract

open class ResultTestPresenter(
    var context: Context,
    var resultTestContractView: ResultTestContract.View
) : ResultTestContract.Presenter {

    override fun getResult(result: Int, questionCount: Int) {
        val str: String = if (result >= (questionCount / 2)) {
            "Твой результат - $result/$questionCount. Молодец!"
        } else {
            "Твой результат - $result/$questionCount. Нужно поднажать!"
        }
        resultTestContractView.showResult(str)
    }

}