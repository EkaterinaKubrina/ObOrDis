package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.view.SelectLevelContractView

class SelectLevelPresenter(
    var context: Context,
    var selectLevelContractView: SelectLevelContractView
) {
    private val userModel = UsersModel(context)

    fun selectLevel(id: String, level: Int) {
        userModel.setLevel(id, level)
        selectLevelContractView.nextActivity(id, level)
    }
}