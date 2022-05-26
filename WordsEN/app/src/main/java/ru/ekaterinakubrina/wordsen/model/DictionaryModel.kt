package ru.ekaterinakubrina.wordsen.model

import android.database.sqlite.SQLiteConstraintException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.dao.DictionaryDao
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*

open class DictionaryModel(
    private val dictionaryDao: DictionaryDao,
    private val wordsModel: WordsModel
) {

    fun getWord(uid: String, lvl: Int): WordDto {
        return dictionaryDao.getWord(uid, lvl)
    }

    fun addWordToUser(uid: String, word: WordDto): WordDto {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()
        dictionaryDao.addWordToUser(uid, word, MyDbWordsEN.Dictionary.NEW)
        saveNewWordToFirebase(uid, word.wordId, currentDate, MyDbWordsEN.Dictionary.NEW)
        return word
    }

    fun addWordFromFB(uid: String, idWord: Int, date: Int, status: Int) {
        dictionaryDao.addWordToUserFromFB(uid, idWord, date, status)
    }

    fun getLastDateAddedWord(uid: String): Int? {
        return dictionaryDao.getLastDateAddedWord(uid)
    }

    fun getWordByDate(uid: String, date: Int): WordDto {
        return dictionaryDao.getWordByDate(uid, date)
    }

    fun addUsersWord(word: WordDto, level: Int, uid: String): Boolean {
        try {
            val id = wordsModel.addWord(word, level)
            if (id != null && id.compareTo(-1) != 0) {
                dictionaryDao.addWordToUser(uid, word, MyDbWordsEN.Dictionary.STUDIED)
                return true
            }
        } catch (e: SQLiteConstraintException) {
            val id1 = wordsModel.getIdByWord(word.word)
            if (id1.compareTo(-1) != 0) {
                dictionaryDao.addWordToUser(uid, word, MyDbWordsEN.Dictionary.STUDIED)
                return true
            }
        }
        return false
    }


    fun alreadyKnowWord(uid: String, idWord: Int, date: Int) {
        dictionaryDao.alreadyKnowWord(uid, idWord, date)
        saveStudiedWordToFirebase(uid, idWord, date)
    }

    fun deleteNewAndBadStudiedWords(uid: String) {
        dictionaryDao.deleteNewAndBadStudiedWords(uid)
        deleteNewWordFirebase(uid)
    }

    fun setStatusWord(uid: String, idWord: Int, status: Int, statusOld: Int) {
        dictionaryDao.setStatusWord(uid, idWord, status)
        saveNewStatusToFirebase(uid, idWord, status, statusOld)
    }

    fun deleteWordFromUser(uid: String, idWord: Int) {
        dictionaryDao.deleteWordFromUser(uid, idWord)
        deleteWordFirebase(uid, idWord)
    }

    fun getCountNewWord(uid: String): Int? {
        return dictionaryDao.getCountNewWord(uid)
    }


    fun getCountStudiedWord(uid: String): Int? {
        return dictionaryDao.getCountStudiedWord(uid)
    }

    fun getUserNewWords(uid: String): ArrayList<WordDto> {
        return dictionaryDao.getUserNewWords(uid)

    }

    fun getUserStudiedWords(uid: String): ArrayList<WordDto> {
        return dictionaryDao.getUserStudiedWords(uid)
    }


    open fun saveNewWordToFirebase(uid: String, idWord: Int, date: Int, status: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key
        ref.child(uid)

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["date"] = date
        hopperUpdates["status"] = status

        ref.child(uid).child("new_words").child(idWord.toString()).updateChildren(hopperUpdates)
    }

    open fun saveNewStatusToFirebase(uid: String, idWord: Int, status: Int, statusOld: Int) {
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
                            saveStudiedWordToFirebase(uid, idWord, date)
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

    open fun saveStudiedWordToFirebase(uid: String, idWord: Int, date: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users") //key
        ref.child(uid)

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["date"] = date
        hopperUpdates["status"] = MyDbWordsEN.Dictionary.STUDIED

        ref.child(uid).child("new_words").child(idWord.toString()).removeValue()
        ref.child(uid).child("studied_words").child(idWord.toString()).updateChildren(hopperUpdates)
    }

    open fun deleteNewWordFirebase(uid: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")
        ref.child(uid).child("new_words").removeValue()
    }

    open fun deleteWordFirebase(uid: String, idWord: Int) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("users")
        ref.child(uid).child("new_words").child(idWord.toString()).removeValue()
    }

}