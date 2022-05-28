package ru.ekaterinakubrina.wordsen.mvp.model

import android.content.Context
import ru.ekaterinakubrina.wordsen.mvp.contracts.*
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.service.DictionaryService
import ru.ekaterinakubrina.wordsen.service.UserService
import ru.ekaterinakubrina.wordsen.service.WordService
import java.util.*

class Repository() : DictionaryContract.Model, EntryContract.Model, MainEntryContract.Model,
    RegistrationContract.Model, SelectLevelContract.Model, SelectTestContract.Model,
    SplashContract.Model, TestContract.Model, TestingWordContract.Model {
    companion object {
        private lateinit var instance: Repository
        private lateinit var userService: UserService
        private lateinit var dictionaryService: DictionaryService
        private lateinit var wordService: WordService
        private lateinit var myDbManager: MyDbManager

        fun getRepository(context: Context): Repository {
            if (!::instance.isInitialized) {
                instance = Repository(context)
            }
            return instance
        }

    }

    private constructor(context: Context) : this() {
        myDbManager = MyDbManager(context)
        userService = UserService(UserDaoImpl(myDbManager))
        wordService = WordService(WordDaoImpl(myDbManager))
        dictionaryService = DictionaryService(DictionaryDaoImpl(myDbManager), wordService)
    }

    override fun getDictionary(uid: String): ArrayList<String> {
        return userService.getDictionary(uid)
    }

    override fun addUsersWord(word: WordDto, level: Int, uid: String): Boolean {
        return dictionaryService.addUsersWord(word, level, uid)
    }

    override fun getUserByPasswordAndEmail(email: String, password: String): UserDto? {
        return userService.getUserByPasswordAndEmail(email, password)
    }

    override fun addUser(uid: String, name: String, email: String, password: String) {
        userService.addUser(uid, name, email, password)
    }

    override fun setLevelLocalAndFirebase(uid: String, level: Int) {
        userService.setLevelLocalAndFirebase(uid, level)
    }

    override fun getLevelUser(uid: String): Int? {
        return userService.getLevelUser(uid)
    }

    override fun alreadyKnowWord(uid: String, idWord: Int, date: Int) {
        dictionaryService.alreadyKnowWord(uid, idWord, date)
    }

    override fun getWordByDate(uid: String, date: Int): WordDto {
        return dictionaryService.getWordByDate(uid, date)
    }

    override fun getLastDateAddedWord(uid: String): Int? {
        return dictionaryService.getLastDateAddedWord(uid)
    }

    override fun getWord(uid: String, lvl: Int): WordDto {
        return dictionaryService.getWord(uid, lvl)
    }

    override fun deleteNewAndBadStudiedWords(uid: String) {
        dictionaryService.deleteNewAndBadStudiedWords(uid)
    }

    override fun addWordToUser(uid: String, word: WordDto): WordDto {
        return dictionaryService.addWordToUser(uid, word)
    }

    override fun getCountNewWord(uid: String): Int? {
        return dictionaryService.getCountNewWord(uid)
    }

    override fun getCountStudiedWord(uid: String): Int? {
        return dictionaryService.getCountStudiedWord(uid)
    }

    override fun setLevel(uid: String?, level: Int?) {
        userService.setLevel(uid, level)
    }

    override fun addWordFromFB(uid: String, idWord: Int, date: Int, status: Int) {
        dictionaryService.addWordFromFB(uid, idWord, date, status)
    }

    override fun addUserLocalAndFirebase(
        uid: String,
        name: String,
        email: String,
        password: String
    ) {
        userService.addUserLocalAndFirebase(uid, name, email, password)
    }

    override fun getUser(uid: String): UserDto? {
        return userService.getUser(uid)
    }

    override fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        return wordService.getTranslateForTest(rightTranslate)
    }

    override fun getUserNewWords(uid: String): ArrayList<WordDto> {
        return dictionaryService.getUserNewWords(uid)
    }

    override fun getUserStudiedWords(uid: String): ArrayList<WordDto> {
        return dictionaryService.getUserStudiedWords(uid)
    }

    override fun setStatusWord(uid: String, idWord: Int, status: Int, statusOld: Int) {
        dictionaryService.setStatusWord(uid, idWord, status, statusOld)
    }

    override fun deleteWordFromUser(uid: String, idWord: Int) {
        dictionaryService.deleteWordFromUser(uid, idWord)
    }

}