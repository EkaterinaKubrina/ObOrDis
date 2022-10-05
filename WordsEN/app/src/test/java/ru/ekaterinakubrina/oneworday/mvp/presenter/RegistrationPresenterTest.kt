package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.mvp.contracts.RegistrationContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class RegistrationPresenterTest : TestCase() {
    open class RegistrationActivityMock : RegistrationContract.View {
        override fun setTextError(text: String) {}
        override fun underlineRedName() {}
        override fun underlineRedPassword() {}
        override fun underlineRedRepeatPassword() {}
        override fun underlineRedEmail() {}
        override fun successRegistration() {}
        override fun getName(): String {
            return ""
        }
        override fun getEmail(): String {
            return ""
        }
        override fun getPassword(): String {
            return ""
        }
        override fun getPassword2(): String {
            return ""
        }

        override fun setMyProgressBarVisibility(visible: Boolean) {}
        override fun clearUnderline() {}
    }

    private var registrationActivity = Mockito.mock(RegistrationActivityMock::class.java)
    private var registrationPresenter = RegistrationPresenter(
        ApplicationProvider.getApplicationContext(),
        registrationActivity
    )

    @Test
    fun testCreated() {
        assertNotNull(registrationPresenter)
    }

    @Test
    fun registrationDifferentPassword() {
        Mockito.doReturn("").`when`(registrationActivity).getName()
        Mockito.doReturn("").`when`(registrationActivity).getEmail()
        Mockito.doReturn("111111").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("222222").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)?.setTextError("Введены разные пароли")
        Mockito.verify(registrationActivity)?.underlineRedRepeatPassword()
    }


    @Test
    fun registrationNullName() {
        Mockito.doReturn("").`when`(registrationActivity).getName()
        Mockito.doReturn("").`when`(registrationActivity).getEmail()
        Mockito.doReturn("").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)?.setTextError("Некорректное имя пользователя")
        Mockito.verify(registrationActivity)?.underlineRedName()
    }

    @Test
    fun registrationBadName() {
        Mockito.doReturn("+-!").`when`(registrationActivity).getName()
        Mockito.doReturn("").`when`(registrationActivity).getEmail()
        Mockito.doReturn("").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)?.setTextError("Некорректное имя пользователя")
        Mockito.verify(registrationActivity)?.underlineRedName()
    }


    @Test
    fun registrationNullEmail() {
        Mockito.doReturn("Иван").`when`(registrationActivity).getName()
        Mockito.doReturn("").`when`(registrationActivity).getEmail()
        Mockito.doReturn("111111").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("111111").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)?.setTextError("Некорректный email")
        Mockito.verify(registrationActivity)?.underlineRedEmail()
    }

    @Test
    fun registrationBadEmail() {
        Mockito.doReturn("Иван").`when`(registrationActivity).getName()
        Mockito.doReturn("1@1").`when`(registrationActivity).getEmail()
        Mockito.doReturn("111111").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("111111").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)?.setTextError("Некорректный email")
        Mockito.verify(registrationActivity)?.underlineRedEmail()
    }


    @Test
    fun registrationNullPassword() {
        Mockito.doReturn("Иван").`when`(registrationActivity).getName()
        Mockito.doReturn("Ivan@mail.ru").`when`(registrationActivity).getEmail()
        Mockito.doReturn("").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)
            ?.setTextError("Некорректный пароль (содержит менее 6 знаков или кириллицу)")
        Mockito.verify(registrationActivity)?.underlineRedPassword()
    }

    @Test
    fun registrationBadPassword() {
        Mockito.doReturn("Иван").`when`(registrationActivity).getName()
        Mockito.doReturn("Ivan@mail.ru").`when`(registrationActivity).getEmail()
        Mockito.doReturn("----").`when`(registrationActivity).getPassword2()
        Mockito.doReturn("----").`when`(registrationActivity).getPassword()
        registrationPresenter.clickOnRegistrationButton()
        Mockito.verify(registrationActivity)?.getEmail()
        Mockito.verify(registrationActivity)?.getName()
        Mockito.verify(registrationActivity)?.getPassword()
        Mockito.verify(registrationActivity)?.getPassword2()
        Mockito.verify(registrationActivity)
            ?.setTextError("Некорректный пароль (содержит менее 6 знаков или кириллицу)")
        Mockito.verify(registrationActivity)?.underlineRedPassword()
    }
}