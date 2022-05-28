package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.mvp.contracts.ResultTestContract

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