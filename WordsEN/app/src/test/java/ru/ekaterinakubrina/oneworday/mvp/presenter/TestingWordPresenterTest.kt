package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.TestingWordContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TestingWordPresenterTest : TestCase() {

    open class TestingWordActivityMock : TestingWordContract.View{
        override fun rightAnswer() {}
        override fun wrongAnswer() {}
        override fun nextActivity(uid: String, rightAnswers: Int, listSize: Int) {}
        override fun nextWord(word: String, translate: String, nowIndex: Int, size: Int) {}
    }

    @Test
    fun testCreated() {
        val testingWordActivity = TestingWordActivityMock()
        val presenter =
            TestingWordPresenter(ApplicationProvider.getApplicationContext(), testingWordActivity)
        assertNotNull(presenter)
    }

}