package ru.ekaterinakubrina.wordsen.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertUser(uid: String, name: String, email: String, password: String) {
        val values = ContentValues().apply {
            put(MyDbWordsEN.Users.COLUMN_UID, uid)
            put(MyDbWordsEN.Users.COLUMN_NAME, name)
            put(MyDbWordsEN.Users.COLUMN_EMAIL, email)
            put(MyDbWordsEN.Users.COLUMN_PASSWORD, password)
            put(MyDbWordsEN.Users.COLUMN_LEVEL, 0)
        }
        db?.insert(MyDbWordsEN.Users.TABLE_NAME, null, values)
    }

    fun insertWordToUser(uid: String, wordId: Int, date: Int, status: Int) {
        val values = ContentValues().apply {
            put(MyDbWordsEN.UsersWords.ID_USER, uid)
            put(MyDbWordsEN.UsersWords.ID_WORDS, wordId)
            put(MyDbWordsEN.UsersWords.DATE, date)
            put(MyDbWordsEN.UsersWords.STATUS, status)
        }
        db?.insert(MyDbWordsEN.UsersWords.TABLE2_NAME, null, values)
    }


    fun getUserName(uid: String): String? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_NAME
        )
        var name: String? = null
        val selection: String = MyDbWordsEN.Users.COLUMN_UID + "=?"
        val selectionArgs = arrayOf(uid)
        val cursor = db?.query(
            MyDbWordsEN.Users.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_NAME))
        }
        cursor.close()
        return name
    }


    fun getLevelUser(uid: String): Int? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_LEVEL
        )
        var level: Int? = null
        val selection: String = MyDbWordsEN.Users.COLUMN_UID + "=?"
        val selectionArgs = arrayOf(uid)
        val cursor = db?.query(
            MyDbWordsEN.Users.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            level = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_LEVEL))
        }
        cursor.close()
        return level
    }


    fun getUserByEmailAndPassword(email: String, password: String): UserDto? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_UID,
            MyDbWordsEN.Users.COLUMN_NAME,
            MyDbWordsEN.Users.COLUMN_EMAIL,
            MyDbWordsEN.Users.COLUMN_PASSWORD,
            MyDbWordsEN.Users.COLUMN_LEVEL
        )
        var userDto: UserDto? = null
        val selection: String =
            MyDbWordsEN.Users.COLUMN_EMAIL + "=? AND " + MyDbWordsEN.Users.COLUMN_PASSWORD + "=?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db?.query(
            MyDbWordsEN.Users.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            userDto = UserDto(
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_UID)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_PASSWORD)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_LEVEL))
            )
        }
        cursor.close()
        return userDto
    }

    fun getUserById(uid: String): UserDto? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_NAME,
            MyDbWordsEN.Users.COLUMN_EMAIL,
            MyDbWordsEN.Users.COLUMN_PASSWORD,
            MyDbWordsEN.Users.COLUMN_LEVEL
        )
        var userDto: UserDto? = null
        val selection: String = MyDbWordsEN.Users.COLUMN_UID + "=?"
        val selectionArgs = arrayOf(uid)
        val cursor = db?.query(
            MyDbWordsEN.Users.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            userDto = UserDto(
                uid,
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_PASSWORD)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_LEVEL))
            )
        }
        cursor.close()
        return userDto
    }

    fun getLastDate(uid: String): Int? {
        var date: Int? = null
        val query = ("SELECT " + "MAX(" + MyDbWordsEN.UsersWords.DATE + ")"
                + " FROM " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " =?")
        val selectionArgs = arrayOf(uid)
        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            date =
                cursor.getInt(cursor.getColumnIndexOrThrow("MAX(" + MyDbWordsEN.UsersWords.DATE + ")"))
        }
        cursor.close()
        return date
    }


    fun getCountNewWord(uid: String): Int? {
        var count: Int? = null
        val query = ("SELECT " + "COUNT(" + MyDbWordsEN.UsersWords.ID_WORDS + ")"
                + " FROM " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " =? AND (" + MyDbWordsEN.UsersWords.STATUS + "=? OR " + MyDbWordsEN.UsersWords.STATUS + "=?)")
        val selectionArgs = arrayOf(
            uid,
            MyDbWordsEN.UsersWords.NEW.toString(),
            MyDbWordsEN.UsersWords.BAD_STUDIED.toString()
        )
        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            count =
                cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(" + MyDbWordsEN.UsersWords.ID_WORDS + ")"))
        }
        cursor.close()
        return count
    }

    fun getCountStudiedWord(uid: String): Int? {
        var count: Int? = null
        val query = ("SELECT " + "COUNT(" + MyDbWordsEN.UsersWords.ID_WORDS + ")"
                + " FROM " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " =? AND " + MyDbWordsEN.UsersWords.STATUS + "=?")
        val selectionArgs = arrayOf(uid, MyDbWordsEN.UsersWords.STUDIED.toString())
        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            count =
                cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(" + MyDbWordsEN.UsersWords.ID_WORDS + ")"))
        }
        cursor.close()
        return count
    }

    fun getUserWords(uid: String): ArrayList<String> {
        var word: String
        var translate: String
        val list = ArrayList<String>()
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " = ? ")
        val selectionArgs = arrayOf(uid)
        val cursor = db?.rawQuery(query, selectionArgs)

        while (cursor?.moveToNext()!!) {
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            list.add("$word - $translate")
        }
        cursor.close()
        return list
    }

    fun getUserNewAndBadStudyWords(uid: String): ArrayList<WordDto> {
        var wordId: Int
        var word: String
        var translate: String
        var status: Int
        val list = ArrayList<WordDto>()
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.UsersWords.STATUS + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " = ? AND (" + MyDbWordsEN.UsersWords.STATUS +
                "=? OR " + MyDbWordsEN.UsersWords.STATUS + "=?) AND " + MyDbWordsEN.UsersWords.DATE + "!= ?")
        val selectionArgs = arrayOf(
            uid,
            MyDbWordsEN.UsersWords.NEW.toString(),
            MyDbWordsEN.UsersWords.BAD_STUDIED.toString(),
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(
                Date()
            ).toInt().toString()
        )
        val cursor = db?.rawQuery(query, selectionArgs)

        while (cursor?.moveToNext()!!) {
            wordId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            status = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.UsersWords.STATUS))
            list.add(WordDto(wordId, word, translate, status))
        }
        cursor.close()
        return list
    }


    fun getUserStudiedWords(uid: String): ArrayList<WordDto> {
        var wordId: Int
        var word: String
        var translate: String
        var status: Int
        val list = ArrayList<WordDto>()
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.UsersWords.STATUS + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " +
                MyDbWordsEN.UsersWords.ID_USER + " = ? AND " + MyDbWordsEN.UsersWords.STATUS + "=? ORDER BY RANDOM() LIMIT 14")
        val selectionArgs = arrayOf(uid, MyDbWordsEN.UsersWords.STUDIED.toString())
        val cursor = db?.rawQuery(query, selectionArgs)

        while (cursor?.moveToNext()!!) {
            wordId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            status = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.UsersWords.STATUS))
            list.add(WordDto(wordId, word, translate, status))
        }
        cursor.close()
        return list
    }

    fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        var translate: String
        val list = ArrayList<String>()
        val query = ("SELECT " + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM "
                + MyDbWordsEN.Words.TABLE_NAME + " WHERE " + MyDbWordsEN.Words.COLUMN_TRANSLATE +
                " NOT LIKE '%$rightTranslate%' " + " ORDER BY RANDOM() LIMIT 3")
        val cursor = db?.rawQuery(query, null)

        while (cursor?.moveToNext()!!) {
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            list.add(translate)
        }
        cursor.close()
        return list
    }


    fun getWord(uid: String, level: Int): WordDto {
        var word = ""
        var transcription = ""
        var translate = ""
        var idW = 0
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " + MyDbWordsEN.Words.COLUMN_LEVEL + " = " + level.toString() + " AND (" +
                MyDbWordsEN.UsersWords.ID_USER + " != ?" + " OR " + MyDbWordsEN.UsersWords.ID_USER + " IS NULL)")
        val selectionArgs = arrayOf(uid)
        val cursor = db?.rawQuery(query, selectionArgs)

        while (cursor?.moveToNext()!!) {
            idW = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            transcription =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            break
        }
        cursor.close()
        return WordDto(idW, word, transcription, translate)
    }

    fun getWordByDate(uid: String, date: Int): WordDto {
        var idWord = 0
        var word = ""
        var transcription = ""
        var translate = ""
        val query = ("SELECT "
                + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " + MyDbWordsEN.UsersWords.DATE + " = " + date.toString() + " AND " +
                MyDbWordsEN.UsersWords.ID_USER + " = ?")
        val selectionArgs = arrayOf(uid)
        val cursor = db?.rawQuery(query, selectionArgs)

        while (cursor?.moveToNext()!!) {
            idWord = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            transcription =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            break
        }
        cursor.close()
        return WordDto(idWord, word, transcription, translate)
    }

    fun setLevelUser(uid: String, level: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.Users.COLUMN_LEVEL, level)
        return db!!.update(
            MyDbWordsEN.Users.TABLE_NAME,
            values, MyDbWordsEN.Users.COLUMN_UID + "= ?", arrayOf(uid)
        )
    }

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int, status: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.UsersWords.DATE, date)
        values.put(MyDbWordsEN.UsersWords.STATUS, status)
        return db!!.update(
            MyDbWordsEN.UsersWords.TABLE2_NAME,
            values,
            MyDbWordsEN.UsersWords.ID_USER + "= ? AND " + MyDbWordsEN.UsersWords.ID_WORDS + "=?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun setStatusWord(uid: String, idWord: Int, status: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.UsersWords.STATUS, status)
        return db!!.update(
            MyDbWordsEN.UsersWords.TABLE2_NAME,
            values,
            MyDbWordsEN.UsersWords.ID_USER + "= ? AND " + MyDbWordsEN.UsersWords.ID_WORDS + "=?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun deleteNewAndBadStudiedWords(uid: String): Int {
        return db!!.delete(
            MyDbWordsEN.UsersWords.TABLE2_NAME,
            MyDbWordsEN.UsersWords.ID_USER + "= ? AND (" + MyDbWordsEN.UsersWords.STATUS + "= ? OR " + MyDbWordsEN.UsersWords.STATUS + "= ? )",
            arrayOf(
                uid,
                MyDbWordsEN.UsersWords.NEW.toString(),
                MyDbWordsEN.UsersWords.BAD_STUDIED.toString()
            )
        )
    }


    fun deleteWord(uid: String, idWord: Int): Int {
        return db!!.delete(
            MyDbWordsEN.UsersWords.TABLE2_NAME,
            MyDbWordsEN.UsersWords.ID_USER + "= ? AND " + MyDbWordsEN.UsersWords.ID_WORDS + "= ?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun closeDb() {
        myDbHelper.close()
    }
}