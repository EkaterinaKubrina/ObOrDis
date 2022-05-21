package ru.ekaterinakubrina.wordsen.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.dao.WordDao
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*


class WordPresenter(private val wordDao: WordDao) {


    fun addWord(uid: String, lvl: Int): WordDto {
        val word = wordDao.addWordToUser(uid, lvl)
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()
        saveNewWordToFirebase(uid, word.wordId, currentDate, MyDbWordsEN.UsersWords.NEW)
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
        if (statusOld == MyDbWordsEN.UsersWords.NEW && status == MyDbWordsEN.UsersWords.BAD_STUDIED) {
            ref.child("new_words").child(idWord.toString()).child("status")
                .setValue(status.toString())
        } else {
            val ref2 = FirebaseDatabase.getInstance().getReference("users/$uid")
            ref2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (statusOld == MyDbWordsEN.UsersWords.NEW) {
                        val date =
                            dataSnapshot.child("new_words").child(idWord.toString()).child("date")
                                .getValue(Int::class.java)
                        if (date != null) {
                            saveStudiedWordToFirebase(
                                uid,
                                idWord,
                                date,
                                MyDbWordsEN.UsersWords.STUDIED
                            )
                        }
                    } else if (statusOld == MyDbWordsEN.UsersWords.STUDIED) {
                        val date = dataSnapshot.child("studied_words").child(idWord.toString())
                            .child("date").getValue(Int::class.java)
                        if (date != null) {
                            saveNewWordToFirebase(
                                uid,
                                idWord,
                                date,
                                MyDbWordsEN.UsersWords.BAD_STUDIED
                            )
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            if (statusOld == MyDbWordsEN.UsersWords.NEW) {
                ref.child("new_words").child(idWord.toString()).removeValue()
            } else if (statusOld == MyDbWordsEN.UsersWords.STUDIED) {
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