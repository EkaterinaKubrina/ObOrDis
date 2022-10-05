package ru.ekaterinakubrina.oneworday.mvp.presenter

import junit.framework.TestCase
import org.junit.Test
import ru.ekaterinakubrina.oneworday.mvp.contracts.ForgotPasswordContract

class ForgotPasswordPresenterTest : TestCase() {

    open class ForgotPasswordActivityMock: ForgotPasswordContract.View{
        override fun successSend(email: String) {}
        override fun emailNotExist(email: String) {}
        override fun getEmail(): String {
            return ""
        }
    }

    @Test
    fun testCreated() {
        val forgotPasswordActivity = ForgotPasswordActivityMock()
        val presenter =
            ForgotPasswordPresenter(forgotPasswordActivity)
        assertNotNull(presenter)
    }
}