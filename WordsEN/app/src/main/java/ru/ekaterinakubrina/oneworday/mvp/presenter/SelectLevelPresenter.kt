package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectLevelContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class SelectLevelPresenter(
    var context: Context,
    private var selectLevelContractView: SelectLevelContract.View
) : SelectLevelContract.Presenter {
    private val repository: SelectLevelContract.Model = Repository.getRepository(context)

    override fun selectLevel(id: String, level: Int) {
        repository.setLevelLocalAndFirebase(id, level)
        selectLevelContractView.nextActivity(id, level)
    }

}