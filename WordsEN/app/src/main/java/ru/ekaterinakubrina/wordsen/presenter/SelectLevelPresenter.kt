package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.SelectLevelContract
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.model.UsersModel

open class SelectLevelPresenter(
    var context: Context,
    var selectLevelContractView: SelectLevelContract.View
) : SelectLevelContract.Presenter {
    private val userModel = UsersModel(UserDaoImpl(context))

    override fun selectLevel(id: String, level: Int) {
        userModel.setLevelLocalAndFirebase(id, level)
        selectLevelContractView.nextActivity(id, level)
    }

}