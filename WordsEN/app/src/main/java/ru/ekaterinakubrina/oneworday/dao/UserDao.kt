package ru.ekaterinakubrina.oneworday.dao

import ru.ekaterinakubrina.oneworday.dto.UserDto

interface UserDao {

    fun getUser(uid: String): UserDto?

    fun addUser(uid: String, name: String)

    fun setLevel(uid: String, level: Int): Int

    fun getDictionary(uid: String, language: Int): ArrayList<String>

    fun getLevelUser(uid: String, language: Int): Int?

    fun deleteUser(uid: String): Int

    fun setUserLanguage(uid: String, language: Int)

    fun getLanguageUser(uid: String): Int?

    fun addUserLanguage(uid: String, language: Int)
}