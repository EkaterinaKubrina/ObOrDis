package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectLanguageContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

class SelectLanguagePresenter(
    var context: Context,
    private var selectLanguageContractView: SelectLanguageContract.View
) : SelectLanguageContract.Presenter {
    private val repository: SelectLanguageContract.Model = Repository.getRepository(context)

    override fun clickOnOkButton(uid: String, language: Int) {
        repository.setUserLanguage(uid, language)
        selectLanguageContractView.nextActivity()
    }

    override fun initLanguages() {
        selectLanguageContractView.initSelectSpinner(repository.getLanguages())
    }
}