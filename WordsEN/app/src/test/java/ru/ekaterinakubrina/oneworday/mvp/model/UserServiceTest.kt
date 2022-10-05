package ru.ekaterinakubrina.oneworday.mvp.model

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.oneworday.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.oneworday.data.MyDbManager
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.service.UserService

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class UserServiceTest : TestCase() {
    private val usersModel =
        UserService(UserDaoImpl(MyDbManager(ApplicationProvider.getApplicationContext())))

    @Test
    fun getUser() {
        val userDto = usersModel.getUser("1")
        assertEquals(userDto?.name, "test")
        assertEquals(userDto?.level, 0)
        assertEquals(userDto?.userId, "1")
    }

    @Test
    fun getNullUser() {
        val userDto = usersModel.getUser("2")
        assertEquals(userDto, null)
    }

    @Test
    fun getLevelUser() {
        val level = usersModel.getLevelUser("1")
        assertEquals(null, level)
    }

    @Test
    fun addUser() {
        val usersModelSpy = Mockito.spy(usersModel)
        Mockito.doNothing().`when`(usersModelSpy).saveNameUserToFirebase(anyString(), anyString())

        var user = usersModelSpy.getUser("2")
        assertEquals(user, null)

        usersModelSpy.addUserLocalAndFirebase("2", "user")

        user = usersModelSpy.getUser("2")
        assertEquals(user?.name, "user")
        assertEquals(user?.userId, "2")

        usersModelSpy.deleteUser("2")

        user = usersModelSpy.getUser("2")
        assertEquals(user, null)
    }

    @Test
    fun setLevel() {
        val usersModelSpy = Mockito.spy(usersModel)
        Mockito.doNothing().`when`(usersModelSpy).saveLanguageUserToFirebase(anyString(), anyInt())
        Mockito.doNothing().`when`(usersModelSpy).saveLevelUserToFirebase(anyString(), anyInt())

        var level = usersModelSpy.getLevelUser("1")
        assertEquals(level, null)

        usersModelSpy.setUserLanguage("1", MyDbWordsEN.Language.ENGLISH)
        level = usersModelSpy.getLevelUser("1")
        assertEquals(level, MyDbWordsEN.Level.LEVEL_A0)

        usersModelSpy.setLevel("1", MyDbWordsEN.Level.LEVEL_B1)

        level = usersModelSpy.getLevelUser("1")
        assertEquals(level, MyDbWordsEN.Level.LEVEL_B1)

        usersModelSpy.setLevel("1", MyDbWordsEN.Level.LEVEL_A0)
    }

}