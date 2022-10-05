package ru.ekaterinakubrina.oneworday.dto

class WordDto(val word: String, val translate: String) {
    var wordId: Int = 0
    var transcription: String = ""
    var status: Int = 0
    var language: Int = 0

    constructor(wordId: Int, word: String, transcription: String, translate: String, language: Int) : this(word, translate) {
        this.wordId = wordId
        this.transcription = transcription
        this.language = language
    }

    constructor(wordId: Int, word: String, translate: String, status: Int, language: Int) : this(word, translate) {
        this.wordId = wordId
        this.status = status
        this.language = language
    }
}