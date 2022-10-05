package ru.ekaterinakubrina.oneworday.daoimpl

import ru.ekaterinakubrina.oneworday.dao.UserDao
import ru.ekaterinakubrina.oneworday.data.MyDbManager
import ru.ekaterinakubrina.oneworday.dto.UserDto

class UserDaoImpl(private val myDbManager: MyDbManager) : UserDao {

    override fun getUser(uid: String): UserDto? {
        myDbManager.openDb()
        val user = myDbManager.getUserById(uid)
        myDbManager.closeDb()
        return user
    }


    override fun addUser(uid: String, name: String) {
        myDbManager.openDb()
        myDbManager.insertUser(uid, name)
        myDbManager.closeDb()
    }

    override fun setLevel(uid: String, level: Int): Int {
        myDbManager.openDb()
        val ans = myDbManager.setLevelUser(uid, level, myDbManager.getLanguageUser(uid)!!)
        myDbManager.closeDb()
        return ans
    }

    override fun getDictionary(uid: String, language: Int): ArrayList<String> {
        myDbManager.openDb()
        val list = myDbManager.getUserWords(uid, language)
        myDbManager.closeDb()
        return list
    }

    override fun getLevelUser(uid: String, language: Int): Int? {
        myDbManager.openDb()
        val ans = myDbManager.getLevelUser(uid, language)
        myDbManager.closeDb()
        return ans
    }

    override fun deleteUser(uid: String): Int {
        myDbManager.openDb()
        val ans = myDbManager.deleteUser(uid)
        myDbManager.closeDb()
        return ans
    }

    override fun setUserLanguage(uid: String, language: Int) {
        myDbManager.openDb()
        myDbManager.setUserLanguage(uid, language)
        myDbManager.closeDb()
    }

    override fun getLanguageUser(uid: String): Int? {
        myDbManager.openDb()
        val ans = myDbManager.getLanguageUser(uid)
        myDbManager.closeDb()
        return ans
    }

    override fun addUserLanguage(uid: String, language: Int) {
        myDbManager.openDb()
        myDbManager.addUserLanguage(uid, language)
        myDbManager.closeDb()
    }

}