package ru.ekaterinakubrina.wordsen.presenter

import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mockito
import ru.ekaterinakubrina.wordsen.view.ForgotPasswordActivity

class ForgotPasswordPresenterTest : TestCase() {
    private var forgotPasswordActivity = Mockito.mock(ForgotPasswordActivity::class.java)

    @Test
    fun testCreated() {
        val presenter =
            ForgotPasswordPresenter(forgotPasswordActivity)
        assertNotNull(presenter)
    }
}