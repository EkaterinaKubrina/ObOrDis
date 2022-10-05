package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectTestContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class SelectTestPresenter(
    var context: Context,
    private var selectTestContractView: SelectTestContract.View
) : SelectTestContract.Presenter {
    private val repository: SelectTestContract.Model = Repository.getRepository(context)

    override fun availableTest(uid: String) {
        if (repository.getCountNewWord(uid)!! - 1 < 7) {
            selectTestContractView.setNotAvailableWeekTest()
        } else {
            selectTestContractView.setAvailableWeekTest()
        }

        if (repository.getCountStudiedWord(uid)!! < 14) {
            selectTestContractView.setNotAvailableAllTest()
        } else {
            selectTestContractView.setAvailableAllTest()
        }
    }
}