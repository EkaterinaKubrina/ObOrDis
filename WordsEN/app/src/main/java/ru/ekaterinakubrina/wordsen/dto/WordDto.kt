package ru.ekaterinakubrina.wordsen.dto

class WordDto(val word: String, val translate: String) {
    var wordId: Int = 0
    var transcription: String = ""
    var status: Int = 0

    constructor(wordId: Int, word: String, transcription: String, translate: String) : this(word, translate) {
        this.wordId = wordId
        this.transcription = transcription
    }

    constructor(wordId: Int, word: String, translate: String, status: Int) : this(word, translate) {
        this.wordId = wordId
        this.status = status
    }
}