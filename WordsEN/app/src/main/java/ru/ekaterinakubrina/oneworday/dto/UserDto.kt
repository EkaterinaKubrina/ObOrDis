package ru.ekaterinakubrina.oneworday.dto

class UserDto (var name: String, var level: Int, var language: Int) {
    var userId: String = ""

    constructor(userId: String, name: String, level: Int, language: Int) : this(name, level, language) {
        this.userId = userId
    }


}