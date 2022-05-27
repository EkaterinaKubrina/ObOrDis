package ru.ekaterinakubrina.wordsen.presenter

import junit.framework.TestCase
import org.junit.Test
import ru.ekaterinakubrina.wordsen.view.ForgotPasswordActivity

class ForgotPasswordPresenterTest : TestCase() {

    @Test
    fun testCreated() {
        val forgotPasswordActivity = ForgotPasswordActivity()
        val presenter =
            ForgotPasswordPresenter(forgotPasswordActivity)
        assertNotNull(presenter)
    }
}