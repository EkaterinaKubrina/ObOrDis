package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.mvp.contracts.TestContract
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class TestPresenter(var context: Context, var testContractView: TestContract.View) :
    TestContract.Presenter {
    private val repository: TestContract.Model = Repository.getRepository(context)
    private val french = listOf("La matinée", "La maison", "Rire",
        "Un événement", "L' influence", "La malchance",
        "Jovial", "Oublieux", "Péjoratif",
        "Vigilance")

    override fun clickOnEndButton(count: Int, id: String) {
        val level: Int = when {
            count < 1 -> MyDbWordsEN.Level.LEVEL_A0
            count < 3 -> MyDbWordsEN.Level.LEVEL_A1
            count < 4 -> MyDbWordsEN.Level.LEVEL_A2
            count < 7 -> MyDbWordsEN.Level.LEVEL_B1
            count < 11 -> MyDbWordsEN.Level.LEVEL_B2
            else -> MyDbWordsEN.Level.LEVEL_C1
        }
        repository.setLevelLocalAndFirebase(id, level)
        testContractView.nextActivity(id, level)
    }

    override fun initTest(language: Int) {
        if(language == 0){
            return
        }
        else if(language == 1){
            testContractView.setWords(french)
        }
    }

}