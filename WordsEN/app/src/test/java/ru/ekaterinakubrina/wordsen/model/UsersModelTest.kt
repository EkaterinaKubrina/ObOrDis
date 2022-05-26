package ru.ekaterinakubrina.wordsen.model

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.robolectric.annotation.Config
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class UsersModelTest : TestCase() {
    private val usersModel = UsersModel(UserDaoImpl(ApplicationProvider.getApplicationContext()))

    @Test
    fun getUserByPasswordAndEmail() {
        val userDto = usersModel.getUserByPasswordAndEmail("test@ru", "111111")
        assertEquals(userDto?.name, "test")
        assertEquals(userDto?.email, "test@ru")
        assertEquals(userDto?.level, 1)
        assertEquals(userDto?.password, "111111")
        assertEquals(userDto?.userId, "1")
    }

    @Test
    fun getNullUserByPasswordAndEmail() {
        val userDto = usersModel.getUserByPasswordAndEmail("noUser", "111111")
        assertEquals(userDto, null)
    }

    @Test
    fun getUser() {
        val userDto = usersModel.getUser("1")
        assertEquals(userDto?.name, "test")
        assertEquals(userDto?.email, "test@ru")
        assertEquals(userDto?.level, MyDbWordsEN.Users.LEVEL_A0)
        assertEquals(userDto?.password, "111111")
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
        assertEquals(level, MyDbWordsEN.Users.LEVEL_A0)
    }

    @Test
    fun addUser() {
        val usersModelSpy = Mockito.spy(usersModel)
        Mockito.doNothing().`when`(usersModelSpy).saveNameUserToFirebase(anyString(), anyString())

        var user = usersModelSpy.getUser("2")
        assertEquals(user, null)

        usersModelSpy.addUserLocalAndFirebase("2", "user", "user@ru", "222222")

        user = usersModelSpy.getUser("2")
        assertEquals(user?.name, "user")
        assertEquals(user?.email, "user@ru")
        assertEquals(user?.password, "222222")
        assertEquals(user?.userId, "2")

        usersModelSpy.deleteUser("2")

        user = usersModelSpy.getUser("2")
        assertEquals(user, null)
    }

    @Test
    fun setLevel() {
        var level = usersModel.getLevelUser("1")
        assertEquals(level, MyDbWordsEN.Users.LEVEL_A0)

        usersModel.setLevel("1", MyDbWordsEN.Users.LEVEL_B1)

        level = usersModel.getLevelUser("1")
        assertEquals(level, MyDbWordsEN.Users.LEVEL_B1)

        usersModel.setLevel("1", MyDbWordsEN.Users.LEVEL_A0)
    }

}