package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.model.User
import java.util.*


class UserPresenter(context: Context) {
    private val userModel = User(context)


    fun getDictionary(uid: String): ArrayList<String> {
        return userModel.getDictionary(uid)
    }

    fun signIn(email: String, password: String): UserDto? {
        return userModel.signIn(email, password)
    }


    fun getUser(uid: String): UserDto? {
        return userModel.getUser(uid)
    }

    fun getLevelUser(uid: String): Int? {
        return userModel.getLevelUser(uid)
    }

    fun getNameUser(uid: String): String? {
        return userModel.getNameUser(uid)
    }

    fun addUser(uid: String, name: String, email: String, password: String) {
        userModel.addUser(uid, name, email, password)
    }

    fun addUserLocalDB(uid: String, name: String?, email: String, password: String) {
        userModel.addUserLocalDB(uid, name, email, password)
    }

    fun setLevel(uid: String, level: Int): Int {
        return userModel.setLevel(uid, level)
    }

    fun setLevelLocalDB(uid: String?, level: Int?) {
        userModel.setLevelLocalDB(uid, level)
    }


}