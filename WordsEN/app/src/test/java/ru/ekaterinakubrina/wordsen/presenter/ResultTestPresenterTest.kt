package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.RegistrationActivity
import ru.ekaterinakubrina.wordsen.view.ResultTestActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class ResultTestPresenterTest : TestCase() {
    private var resultTestActivity = Mockito.mock(ResultTestActivity::class.java)
    private var resultTestPresenter = ResultTestPresenter(
        ApplicationProvider.getApplicationContext(),
        resultTestActivity
    )

    @Test
    fun testCreated() {
        val presenter =
            ResultTestPresenter(ApplicationProvider.getApplicationContext(), resultTestActivity)
        assertNotNull(presenter)
    }

    @Test
    fun getBadResult() {
        resultTestPresenter.getResult(5, 10)
        Mockito.verify(resultTestActivity)?.showResult("Твой результат - 5/10. Молодец!")
    }

    @Test
    fun getGoodResult() {
        resultTestPresenter.getResult(4, 10)
        Mockito.verify(resultTestActivity)?.showResult("Твой результат - 4/10. Нужно поднажать!")
    }
}