package ru.ekaterinakubrina.wordsen.view

import ru.ekaterinakubrina.wordsen.dto.UserDto

interface SelectLevelContractView {
    fun nextActivity(id: String, level: Int)
}