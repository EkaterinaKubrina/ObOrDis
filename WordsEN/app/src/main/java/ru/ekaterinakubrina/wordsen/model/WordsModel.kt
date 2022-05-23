package ru.ekaterinakubrina.wordsen.model

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*

class WordsModel(context: Context) {
    private val wordDao = WordDaoImpl(context)

    fun getWord(uid: String, lvl: Int): WordDto {
        return wordDao.getWord(uid, lvl)
    }

    fun addWordToUser(uid: String, word: WordDto): WordDto {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()
        wordDao.addWordToUser(uid, word)
        saveNewWordToFirebase(uid, word.wordId, currentDate, MyDbWordsEN.Dictionary.NEW)
        return word
    }

    fun addWordFromFB(uid: String, idWord: Int, date: Int, status: Int) {
        wordDao.addWordToUserFromFB(uid, idWord, date, status)
    }

    fun getLastDateAddedWord(uid: String): Int? {
        return wordDao.getLastDateAddedWord(uid)
    }

    fun getWordByDate(uid: String, date: Int): WordDto {
        return wordDao.getWordByDate(uid, date)
    }

    fun addUsersWord(word: WordDto, level: Int, uid: String): Boolean {
        try {
            val id = wordDao.addWord(word, level)
            if (id != null && id.compareTo(-1) != 0){
                wordDao.addWordToUser(uid, word)
                return true
            }
        } catch (e: SQLiteConstraintException){
            val id1 = wordDao.getIdByWord(word.word)
            if (id1.compareTo(-1) != 0){
                wordDao.addWordToUser(uid, word)
                return true
            }
        }
        return false
    }

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int, status: Int) {
        wordDao.alreadyKnowWord(uid, idWord, date, status)
        saveStudiedWordToFirebase(uid, idWord, date, status)
    }

    fun deleteNewAndBadStudiedWords(uid: String) {
        wordDao.deleteNewAndBadStudiedWords(uid)
        deleteNewWordFirebase(uid)
    }

    fun setStatusWord(uid: String, idWord: Int, status: Int, statusOld: Int) {
        wordDao.setStatusWord(uid, idWord, status)
        saveNewStatusToFirebase(uid, idWord, status, statusOld)
    }

    fun deleteWord(uid: String, idWord: Int) {
        wordDao.deleteWord(uid, idWord)
        deleteWordFirebase(uid, idWord)
    }

    fun getCountNewWord(uid: String): Int? {
        return wordDao.getCountNewWord(uid)
    }

    fun getCountStudiedWord(uid: String): Int? {
        return wordDao.getCountStudiedWord(uid)
    }

    fun getUserNewWords(uid: String): ArrayList<WordDto> {
        return wordDao.getUserNewWords(uid)

    }

    fun getUserStudiedWords(uid: String): ArrayList<WordDto> {
        return wordDao.getUserStudiedWords(uid)
    }

    fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        return wordDao.getTranslateForTest(rightTranslate)
    }

    private fun saveNewWordToFirebase(uid: String, idWord: Int, date: Int, status: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key
        ref.child(uid)

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["date"] = date
        hopperUpdates["status"] = status

        ref.child(uid).child("new_words").child(idWord.toString()).updateChildren(hopperUpdates)
    }

    private fun saveNewStatusToFirebase(uid: String, idWord: Int, status: Int, statusOld: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users/$uid") //key
        if (statusOld == MyDbWordsEN.Dictionary.NEW && status == MyDbWordsEN.Dictionary.BAD_STUDIED) {
            ref.child("new_words").child(idWord.toString()).child("status")
                .setValue(status.toString())
        } else {
            val ref2 = FirebaseDatabase.getInstance().getReference("users/$uid")
            ref2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (statusOld == MyDbWordsEN.Dictionary.NEW) {
                        val date =
                            dataSnapshot.child("new_words").child(idWord.toString()).child("date")
                                .getValue(Int::class.java)
                        if (date != null) {
                            saveStudiedWordToFirebase(
                                uid,
                                idWord,
                                date,
                                MyDbWordsEN.Dictionary.STUDIED
                            )
                        }
                    } else if (statusOld == MyDbWordsEN.Dictionary.STUDIED) {
                        val date = dataSnapshot.child("studied_words").child(idWord.toString())
                            .child("date").getValue(Int::class.java)
                        if (date != null) {
                            saveNewWordToFirebase(
                                uid,
                                idWord,
                                date,
                                MyDbWordsEN.Dictionary.BAD_STUDIED
                            )
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            if (statusOld == MyDbWordsEN.Dictionary.NEW) {
                ref.child("new_words").child(idWord.toString()).removeValue()
            } else if (statusOld == MyDbWordsEN.Dictionary.STUDIED) {
                ref.child("studied_words").child(idWord.toString()).removeValue()
            }
        }
    }

    private fun saveStudiedWordToFirebase(uid: String, idWord: Int, date: Int, status: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key
        ref.child(uid)

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["date"] = date
        hopperUpdates["status"] = status


        ref.child(uid).child("new_words").child(idWord.toString()).removeValue()
        ref.child(uid).child("studied_words").child(idWord.toString()).updateChildren(hopperUpdates)
    }

    private fun deleteNewWordFirebase(uid: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")
        ref.child(uid).child("new_words").removeValue()
    }

    private fun deleteWordFirebase(uid: String, idWord: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")
        ref.child(uid).child("new_words").child(idWord.toString()).removeValue()
    }

}