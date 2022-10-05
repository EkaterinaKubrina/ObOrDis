package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.mvp.contracts.ResultTestContract

open class ResultTestPresenter(
    var context: Context,
    private var resultTestContractView: ResultTestContract.View
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