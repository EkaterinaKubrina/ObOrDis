package ru.ekaterinakubrina.wordsen.view

import ru.ekaterinakubrina.wordsen.dto.UserDto

interface EntryContractView {
    fun setError(message: String)
    fun nextActivity(userDto: UserDto)
}