package ru.ekaterinakubrina.wordsen.daoimpl

import android.content.Context
import ru.ekaterinakubrina.wordsen.dao.UserDao
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import ru.ekaterinakubrina.wordsen.dto.UserDto

class UserDaoImpl(context: Context) : UserDao {
    private val myDbManager = MyDbManager(context)

    override fun getUser(email: String, password: String): UserDto? {
        myDbManager.openDb()
        val user = myDbManager.getUserByEmailAndPassword(email, password)
        myDbManager.closeDb()
        return user
    }

    override fun getUser(uid: String): UserDto? {
        myDbManager.openDb()
        val user = myDbManager.getUserById(uid)
        myDbManager.closeDb()
        return user
    }


    override fun addUser(uid: String, name: String, email: String, password: String) {
        myDbManager.openDb()
        myDbManager.insertUser(uid, name, email, password)
        myDbManager.closeDb()
    }

    override fun setLevel(uid: String, level: Int): Int {
        myDbManager.openDb()
        val ans = myDbManager.setLevelUser(uid, level)
        myDbManager.closeDb()
        return ans
    }

    override fun getDictionary(uid: String): ArrayList<String> {
        myDbManager.openDb()
        val list = myDbManager.getUserWords(uid)
        myDbManager.closeDb()
        return list
    }

    override fun getLevelUser(uid: String): Int? {
        myDbManager.openDb()
        val ans = myDbManager.getLevelUser(uid)
        myDbManager.closeDb()
        return ans
    }

    override fun deleteUser(uid: String): Int {
        myDbManager.openDb()
        val ans = myDbManager.deleteUser(uid)
        myDbManager.closeDb()
        return ans
    }

}