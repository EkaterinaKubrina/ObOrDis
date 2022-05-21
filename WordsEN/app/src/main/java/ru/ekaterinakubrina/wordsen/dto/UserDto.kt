package ru.ekaterinakubrina.wordsen.dto

class UserDto (var name: String, var email: String, var password: String, var level: Int) {
    var userId: String = ""

    constructor(userId: String, name: String, email: String, password: String, level: Int) : this(name, email, password, level) {
        this.userId = userId
    }


}