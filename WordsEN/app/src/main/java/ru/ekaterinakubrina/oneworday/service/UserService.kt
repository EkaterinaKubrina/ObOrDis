package ru.ekaterinakubrina.oneworday.service

import com.google.firebase.database.FirebaseDatabase
import ru.ekaterinakubrina.oneworday.dao.UserDao
import ru.ekaterinakubrina.oneworday.dto.UserDto
import java.util.*

open class UserService(private val userDao: UserDao) {

    fun getDictionary(uid: String): ArrayList<String> {
        return userDao.getDictionary(uid, getLanguageUser(uid)!!)
    }

    fun getUser(uid: String): UserDto? {
        return userDao.getUser(uid)
    }

    fun getLevelUser(uid: String): Int? {
        return userDao.getLevelUser(uid, userDao.getLanguageUser(uid)!!)
    }

    fun getLanguageUser(uid: String): Int? {
        return userDao.getLanguageUser(uid)
    }

    fun addUserLocalAndFirebase(uid: String, name: String) {
        saveNameUserToFirebase(uid, name)
        addUser(uid, name)
    }

    fun addUser(uid: String, name: String) {
        userDao.addUser(uid, name)
    }

    fun setUserLanguage(uid: String, language: Int) {
        if (userDao.getLevelUser(uid, language) == null) {
            userDao.addUserLanguage(uid, language)
        }
        userDao.setUserLanguage(uid, language)
        saveLanguageUserToFirebase(uid, language)
        saveLevelUserToFirebase(uid, userDao.getLevelUser(uid, language)!!)
    }

    fun deleteUser(uid: String) {
        userDao.deleteUser(uid)
    }

    fun setLevelLocalAndFirebase(uid: String, level: Int) {
        saveLevelUserToFirebase(uid, level)
        setLevel(uid, level)
    }

    fun setLevel(uid: String?, level: Int?) {
        if (uid != null && level != null) {
            userDao.setLevel(uid, level)
        }
    }

    open fun saveNameUserToFirebase(uid: String, name: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["name"] = name

        ref.child(uid).updateChildren(hopperUpdates)
    }

    open fun saveLevelUserToFirebase(uid: String, level: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["level"] = level

        ref.child(uid).updateChildren(hopperUpdates)
    }

    open fun saveLanguageUserToFirebase(uid: String, language: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["language"] = language

        ref.child(uid).updateChildren(hopperUpdates)
    }
}