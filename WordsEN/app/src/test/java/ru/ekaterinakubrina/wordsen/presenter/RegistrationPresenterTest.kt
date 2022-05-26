package ru.ekaterinakubrina.wordsen.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.view.RegistrationActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class RegistrationPresenterTest : TestCase() {
    private var registrationActivity = Mockito.mock(RegistrationActivity::class.java)
    private var registrationPresenter = RegistrationPresenter(
        ApplicationProvider.getApplicationContext(),
        registrationActivity
    )

    @Test
    fun testCreated() {
        val presenter =
            RegistrationPresenter(ApplicationProvider.getApplicationContext(), registrationActivity)
        assertNotNull(presenter)
    }

    @Test
    fun registrationDifferentPassword() {
        registrationPresenter.registration("", "", "1", "2")
        Mockito.verify(registrationActivity)?.setTextError("Введены разные пароли")
        Mockito.verify(registrationActivity)?.underlineRedRepeatPassword()
    }


    @Test
    fun registrationNullName() {
        registrationPresenter.registration("", "", "111111", "111111")
        Mockito.verify(registrationActivity)?.setTextError("Некорректное имя пользователя")
        Mockito.verify(registrationActivity)?.underlineRedName()
    }

    @Test
    fun registrationBadName() {
        registrationPresenter.registration("+-!", "", "111111", "111111")
        Mockito.verify(registrationActivity)?.setTextError("Некорректное имя пользователя")
        Mockito.verify(registrationActivity)?.underlineRedName()
    }


    @Test
    fun registrationNullEmail() {
        registrationPresenter.registration("Иван", "", "111111", "111111")
        Mockito.verify(registrationActivity)?.setTextError("Некорректный email")
        Mockito.verify(registrationActivity)?.underlineRedEmail()
    }

    @Test
    fun registrationBadEmail() {
        registrationPresenter.registration("Иван", "1@1", "111111", "111111")
        Mockito.verify(registrationActivity)?.setTextError("Некорректный email")
        Mockito.verify(registrationActivity)?.underlineRedEmail()
    }


    @Test
    fun registrationNullPassword() {
        registrationPresenter.registration("Иван", "Ivan@mail.ru", "", "")
        Mockito.verify(registrationActivity)
            ?.setTextError("Некорректный пароль (содержит менее 6 знаков или кириллицу)")
        Mockito.verify(registrationActivity)?.underlineRedPassword()
    }

    @Test
    fun registrationBadPassword() {
        registrationPresenter.registration("Иван", "Ivan@mail.ru", "----", "----")
        Mockito.verify(registrationActivity)
            ?.setTextError("Некорректный пароль (содержит менее 6 знаков или кириллицу)")
        Mockito.verify(registrationActivity)?.underlineRedPassword()
    }
}