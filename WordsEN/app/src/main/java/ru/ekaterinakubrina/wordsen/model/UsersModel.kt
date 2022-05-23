package ru.ekaterinakubrina.wordsen.model

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.dto.UserDto
import java.util.*

class UsersModel(context: Context) {
    private val userDao = UserDaoImpl(context)

    fun getDictionary(uid: String): ArrayList<String> {
        return userDao.getDictionary(uid)
    }

    fun signIn(email: String, password: String): UserDto? {
        return userDao.getUser(email, password)
    }


    fun getUser(uid: String): UserDto? {
        return userDao.getUser(uid)
    }

    fun getLevelUser(uid: String): Int? {
        return userDao.getLevelUser(uid)
    }

    fun getNameUser(uid: String): String? {
        return userDao.getNameUser(uid)
    }

    fun addUser(uid: String, name: String, email: String, password: String) {
        saveNameUserToFirebase(uid, name)
        userDao.addUser(uid, name, email, password)
    }

    fun addUserLocalDB(uid: String, name: String?, email: String, password: String) {
        if (name != null) {
            userDao.addUser(uid, name, email, password)
        }
    }

    fun setLevel(uid: String, level: Int): Int {
        saveLevelUserToFirebase(uid, level)
        return userDao.setLevel(uid, level)
    }

    fun setLevelLocalDB(uid: String?, level: Int?) {
        if (uid != null && level != null) {
            userDao.setLevel(uid, level)
        }
    }

    private fun saveNameUserToFirebase(uid: String, name: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["name"] = name

        ref.child(uid).updateChildren(hopperUpdates)
    }

    private fun saveLevelUserToFirebase(uid: String, level: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["level"] = level

        ref.child(uid).updateChildren(hopperUpdates)
    }
}