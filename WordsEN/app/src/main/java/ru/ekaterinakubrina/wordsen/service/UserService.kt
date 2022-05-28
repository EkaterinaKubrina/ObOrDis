package ru.ekaterinakubrina.wordsen.service

import com.google.firebase.database.FirebaseDatabase
import ru.ekaterinakubrina.wordsen.dao.UserDao
import ru.ekaterinakubrina.wordsen.dto.UserDto
import java.util.*

open class UserService(private val userDao: UserDao) {

    fun getDictionary(uid: String): ArrayList<String> {
        return userDao.getDictionary(uid)
    }

    fun getUserByPasswordAndEmail(email: String, password: String): UserDto? {
        return userDao.getUser(email, password)
    }

    fun getUser(uid: String): UserDto? {
        return userDao.getUser(uid)
    }

    fun getLevelUser(uid: String): Int? {
        return userDao.getLevelUser(uid)
    }

    fun addUserLocalAndFirebase(uid: String, name: String, email: String, password: String) {
        saveNameUserToFirebase(uid, name)
        addUser(uid, name, email, password)
    }

    fun addUser(uid: String, name: String, email: String, password: String) {
        userDao.addUser(uid, name, email, password)
    }

    fun deleteUser(uid: String) {
        userDao.deleteUser(uid)
    }

    fun setLevelLocalAndFirebase(uid: String, level: Int){
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
        val ref = db.getReference("users") //key

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["name"] = name

        ref.child(uid).updateChildren(hopperUpdates)
    }

    open fun saveLevelUserToFirebase(uid: String, level: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["level"] = level

        ref.child(uid).updateChildren(hopperUpdates)
    }
}