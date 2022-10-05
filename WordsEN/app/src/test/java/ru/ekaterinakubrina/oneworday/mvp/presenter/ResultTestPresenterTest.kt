package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.ResultTestContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class ResultTestPresenterTest : TestCase() {
    open class ResultTestActivityMock : ResultTestContract.View {
        override fun showResult(str: String) {
        }
    }

    private var resultTestActivity = Mockito.mock(ResultTestActivityMock::class.java)
    private var resultTestPresenter = ResultTestPresenter(
        ApplicationProvider.getApplicationContext(),
        resultTestActivity
    )

    @Test
    fun testCreated() {
        assertNotNull(resultTestPresenter)
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