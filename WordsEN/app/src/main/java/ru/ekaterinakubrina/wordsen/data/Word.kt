package ru.ekaterinakubrina.wordsen.data

class Word( val word: String, val transcription: String, val translate: String) {
    var wordId: Int = 0

    constructor( wordId:Int, word: String,  transcription: String,  translate: String) : this(word, transcription, translate) {
        this.wordId = wordId

    }
}