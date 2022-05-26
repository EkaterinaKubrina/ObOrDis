package ru.ekaterinakubrina.wordsen.dao

import ru.ekaterinakubrina.wordsen.dto.UserDto

interface UserDao {
    fun getUser(email: String, password: String): UserDto?

    fun getUser(uid: String): UserDto?

    fun addUser(uid: String, name: String, email: String, password: String)

    fun setLevel(uid: String, level: Int): Int

    fun getDictionary(uid: String): ArrayList<String>

    fun getLevelUser(uid: String): Int?

    fun deleteUser(uid: String): Int
}