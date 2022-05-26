package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.contracts.TestContract
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.model.UsersModel

open class TestPresenter(var context: Context, var testContractView: TestContract.View) :
    TestContract.Presenter {
    private val usersModel = UsersModel(UserDaoImpl(context))

    override fun setLevel(count: Int, id: String) {
        val level: Int = when {
            count < 1 -> MyDbWordsEN.Users.LEVEL_A0
            count < 3 -> MyDbWordsEN.Users.LEVEL_A1
            count < 4 -> MyDbWordsEN.Users.LEVEL_A2
            count < 7 -> MyDbWordsEN.Users.LEVEL_B1
            count < 11 -> MyDbWordsEN.Users.LEVEL_B2
            else -> MyDbWordsEN.Users.LEVEL_C1
        }
        usersModel.setLevelLocalAndFirebase(id, level)
        testContractView.nextActivity(id, level)
    }

}