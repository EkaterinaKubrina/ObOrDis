package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.mvp.contracts.TestContract
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.mvp.model.Repository

open class TestPresenter(var context: Context, var testContractView: TestContract.View) :
    TestContract.Presenter {
    private val repository: Repository = Repository.getRepository(context)

    override fun clickOnEndButton(count: Int, id: String) {
        val level: Int = when {
            count < 1 -> MyDbWordsEN.Users.LEVEL_A0
            count < 3 -> MyDbWordsEN.Users.LEVEL_A1
            count < 4 -> MyDbWordsEN.Users.LEVEL_A2
            count < 7 -> MyDbWordsEN.Users.LEVEL_B1
            count < 11 -> MyDbWordsEN.Users.LEVEL_B2
            else -> MyDbWordsEN.Users.LEVEL_C1
        }
        repository.setLevelLocalAndFirebase(id, level)
        testContractView.nextActivity(id, level)
    }

}