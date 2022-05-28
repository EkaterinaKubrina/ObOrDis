package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.mvp.contracts.SelectLevelContract
import ru.ekaterinakubrina.wordsen.mvp.model.Repository

open class SelectLevelPresenter(
    var context: Context,
    var selectLevelContractView: SelectLevelContract.View
) : SelectLevelContract.Presenter {
    private val repository: Repository = Repository.getRepository(context)

    override fun selectLevel(id: String, level: Int) {
        repository.setLevelLocalAndFirebase(id, level)
        selectLevelContractView.nextActivity(id, level)
    }

}