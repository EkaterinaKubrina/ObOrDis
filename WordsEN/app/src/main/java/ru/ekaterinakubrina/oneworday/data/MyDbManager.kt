package ru.ekaterinakubrina.oneworday.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import ru.ekaterinakubrina.oneworday.dto.UserDto
import ru.ekaterinakubrina.oneworday.dto.WordDto
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertUser(uid: String, name: String) {
        val values = ContentValues().apply {
            put(MyDbWordsEN.Users.COLUMN_UID, uid)
            put(MyDbWordsEN.Users.COLUMN_NAME, name)
        }
        Log.i("Insert", values.toString())
        db?.insert(MyDbWordsEN.Users.TABLE_NAME, null, values)
    }

    fun insertWordToUser(uid: String, wordId: Int, date: Int, status: Int) {
        val values = ContentValues().apply {
            put(MyDbWordsEN.Dictionary.ID_USER, uid)
            put(MyDbWordsEN.Dictionary.ID_WORDS, wordId)
            put(MyDbWordsEN.Dictionary.DATE, date)
            put(MyDbWordsEN.Dictionary.STATUS, status)
        }
        Log.i("Insert", values.toString())
        db?.insert(MyDbWordsEN.Dictionary.TABLE_NAME, null, values)
    }

    fun deleteUser(uid: String): Int {
        return db!!.delete(
            MyDbWordsEN.Users.TABLE_NAME,
            MyDbWordsEN.Users.COLUMN_UID + " = ?",
            arrayOf(uid)
        )
    }

    fun getLevelUser(uid: String, language: Int): Int? {
        val projection = arrayOf(
            MyDbWordsEN.UsersLanguages.ID_LEVEL
        )
        var level: Int? = null
        val selection: String =
            MyDbWordsEN.UsersLanguages.ID_USER + "=? AND " + MyDbWordsEN.UsersLanguages.ID_LANGUAGE + "=?"
        val selectionArgs = arrayOf(uid, language.toString())

        val cursor = db?.query(
            MyDbWordsEN.UsersLanguages.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            level = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.UsersLanguages.ID_LEVEL))
        }
        cursor.close()

        return level
    }

    fun getLanguageUser(uid: String): Int? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_LANGUAGE
        )
        var language: Int? = null
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
            language =
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_LANGUAGE))
        }
        cursor.close()

        return language
    }

    fun getUserById(uid: String): UserDto? {
        var userDto: UserDto? = null
        val selectionArgs = arrayOf(uid)
        val query = ("SELECT " + MyDbWordsEN.Users.COLUMN_NAME + ", "
                + MyDbWordsEN.Users.COLUMN_LANGUAGE + ", "
                + MyDbWordsEN.UsersLanguages.ID_LEVEL +
                " FROM " + MyDbWordsEN.Users.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersLanguages.TABLE_NAME +
                " ON " + MyDbWordsEN.Users.TABLE_NAME + "." + MyDbWordsEN.Users.COLUMN_UID
                + " = " + MyDbWordsEN.UsersLanguages.TABLE_NAME + "." + MyDbWordsEN.UsersLanguages.ID_USER + " AND "
                + MyDbWordsEN.Users.TABLE_NAME + "." + MyDbWordsEN.Users.COLUMN_LANGUAGE
                + " = " + MyDbWordsEN.UsersLanguages.TABLE_NAME + "." + MyDbWordsEN.UsersLanguages.ID_LANGUAGE +
                " WHERE " + MyDbWordsEN.Users.COLUMN_UID + " = ?")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            userDto = UserDto(
                uid,
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.UsersLanguages.ID_LEVEL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Users.COLUMN_LANGUAGE))
            )
        }
        cursor.close()

        return userDto
    }

    fun getLastDateAddedWord(uid: String, language: Int): Int? {
        var date: Int? = null
        val selectionArgs = arrayOf(uid, language.toString())
        val query = ("SELECT " + "MAX(" + MyDbWordsEN.Dictionary.DATE + ")"
                + " FROM " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Words.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER + " =? AND "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE + " =?")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            date =
                cursor.getInt(cursor.getColumnIndexOrThrow("MAX(" + MyDbWordsEN.Dictionary.DATE + ")"))
        }
        cursor.close()

        return date
    }

    fun getCountNewWord(uid: String, language: Int): Int? {
        var count: Int? = null
        val selectionArgs = arrayOf(
            uid,
            language.toString(),
            MyDbWordsEN.Status.NEW.toString(),
            MyDbWordsEN.Status.BAD_STUDIED.toString()
        )
        val query = ("SELECT " + "COUNT(" + MyDbWordsEN.Dictionary.ID_WORDS + ")"
                + " FROM " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Words.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER
                + " =? AND " + MyDbWordsEN.Words.COLUMN_LANGUAGE
                + "=? AND (" + MyDbWordsEN.Dictionary.STATUS
                + "=? OR " + MyDbWordsEN.Dictionary.STATUS + "=?)")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            count =
                cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(" + MyDbWordsEN.Dictionary.ID_WORDS + ")"))
        }
        cursor.close()

        return count
    }

    fun getLanguages(): ArrayList<String> {
        val list = ArrayList<String>()
        val query =
            ("SELECT DISTINCT " + MyDbWordsEN.Language.TABLE_NAME + "." + MyDbWordsEN.Language.LANGUAGE
                    + " FROM " + MyDbWordsEN.Language.TABLE_NAME
                    + " LEFT OUTER JOIN " + MyDbWordsEN.Words.TABLE_NAME
                    + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + MyDbWordsEN.Words.COLUMN_LANGUAGE
                    + " = " + MyDbWordsEN.Language.TABLE_NAME + "." + MyDbWordsEN.Language.ID_LANGUAGE)
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, null)
        while (cursor?.moveToNext()!!) {
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_LANGUAGE)))
        }
        cursor.close()

        return list
    }

    fun getCountStudiedWord(uid: String, language: Int): Int? {
        var count: Int? = null
        val selectionArgs = arrayOf(uid, MyDbWordsEN.Status.STUDIED.toString(), language.toString())
        val query = ("SELECT " + "COUNT(" + MyDbWordsEN.Dictionary.ID_WORDS + ")"
                + " FROM " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Words.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER
                + " =? AND " + MyDbWordsEN.Dictionary.STATUS + "=?  AND "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE + "=?")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            count =
                cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(" + MyDbWordsEN.Dictionary.ID_WORDS + ")"))
        }
        cursor.close()

        return count
    }

    fun getUserWords(uid: String, language: Int): ArrayList<String> {
        var word: String
        var translate: String
        val list = ArrayList<String>()
        val selectionArgs = arrayOf(uid, language.toString())
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE
                + " FROM " + MyDbWordsEN.Words.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER + " = ? AND "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE + " = ?")
        Log.i("Query", query)

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

    fun getUserNewAndBadStudyWords(uid: String, language: Int): ArrayList<WordDto> {
        var wordId: Int
        var word: String
        var translate: String
        var status: Int
        val list = ArrayList<WordDto>()
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.Dictionary.STATUS
                + " FROM " + MyDbWordsEN.Words.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER
                + " = ? AND (" + MyDbWordsEN.Dictionary.STATUS
                + "=? OR " + MyDbWordsEN.Dictionary.STATUS
                + "=?) AND "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE + " = ? AND "
                + MyDbWordsEN.Dictionary.DATE + "!= ?")
        Log.i("Query", query)
        val selectionArgs = arrayOf(
            uid,
            MyDbWordsEN.Status.NEW.toString(),
            MyDbWordsEN.Status.BAD_STUDIED.toString(),
            language.toString(),
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
            status = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Dictionary.STATUS))
            list.add(WordDto(wordId, word, translate, status, language))
        }
        cursor.close()

        return list
    }

    fun getUserStudiedWords(uid: String, language: Int): ArrayList<WordDto> {
        var wordId: Int
        var word: String
        var translate: String
        var status: Int
        val list = ArrayList<WordDto>()
        val selectionArgs = arrayOf(uid, language.toString(), MyDbWordsEN.Status.STUDIED.toString())
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.Dictionary.STATUS
                + " FROM " + MyDbWordsEN.Words.TABLE_NAME
                + " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME
                + " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.ID_USER + " = ? AND "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE + " = ? AND "
                + MyDbWordsEN.Dictionary.STATUS + "=? ORDER BY RANDOM() LIMIT 14")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            wordId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD))
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            status = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Dictionary.STATUS))
            list.add(WordDto(wordId, word, translate, status, language))
        }
        cursor.close()

        return list
    }

    fun getTranslateForTest(rightTranslate: String): ArrayList<String> {
        var translate: String
        val list = ArrayList<String>()
        val query = ("SELECT " + MyDbWordsEN.Words.COLUMN_TRANSLATE
                + " FROM " + MyDbWordsEN.Words.TABLE_NAME
                + " WHERE " + MyDbWordsEN.Words.COLUMN_TRANSLATE +
                " NOT LIKE '%$rightTranslate%' "
                + " ORDER BY RANDOM() LIMIT 3")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, null)
        while (cursor?.moveToNext()!!) {
            translate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            list.add(translate)
        }
        cursor.close()

        return list
    }

    fun getWordForUserByLevel(uid: String, level: Int, language: Int): WordDto {
        var word = ""
        var transcription = ""
        var translate = ""
        var idW = 0
        val selectionArgs = arrayOf(uid)
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE +
                " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS +
                " WHERE " + MyDbWordsEN.Words.COLUMN_LEVEL + " = " + level.toString() +
                " AND " + MyDbWordsEN.Words.COLUMN_LANGUAGE + " = " + language.toString() +
                " AND " + BaseColumns._ID + " NOT IN " +
                " (SELECT " + BaseColumns._ID +
                " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID +
                " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS +
                " WHERE " + MyDbWordsEN.Dictionary.ID_USER + " = ? )")
        Log.i("Query", query)

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

        return WordDto(idW, word, transcription, translate, language)
    }

    fun getIdByWord(word: String): Int {
        var idW = -1
        val selectionArgs = null
        val query = ("SELECT " + BaseColumns._ID +
                " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " WHERE " + MyDbWordsEN.Words.COLUMN_WORD + " = \"" + word + "\"")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            idW = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            break
        }
        cursor.close()

        return idW
    }

    fun getWordById(wordId: Int): WordDto? {
        var word: WordDto? = null
        val selectionArgs = arrayOf(wordId.toString())
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE +
                " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " WHERE " + BaseColumns._ID + " = ?")
        Log.i("Query", query)

        val cursor = db?.rawQuery(query, selectionArgs)
        while (cursor?.moveToNext()!!) {
            word = WordDto(
                cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_WORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_TRANSLATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbWordsEN.Words.COLUMN_LANGUAGE))
            )
            break
        }
        cursor.close()

        return word
    }

    fun getUsersWordByDate(uid: String, date: Int, language: Int): WordDto {
        var idWord = 0
        var word = ""
        var transcription = ""
        var translate = ""
        val selectionArgs = arrayOf(language.toString(), uid)
        val query = ("SELECT "
                + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + ", "
                + MyDbWordsEN.Words.COLUMN_LANGUAGE
                + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.Dictionary.TABLE_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "." + BaseColumns._ID
                + " = " + MyDbWordsEN.Dictionary.TABLE_NAME + "." + MyDbWordsEN.Dictionary.ID_WORDS
                + " WHERE " + MyDbWordsEN.Dictionary.DATE + " = " + date.toString()
                + " AND " + MyDbWordsEN.Words.COLUMN_LANGUAGE
                + " = ? AND " + MyDbWordsEN.Dictionary.ID_USER + " = ?")
        Log.i("Query", query)

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

        return WordDto(idWord, word, transcription, translate, language)
    }

    fun addWord(wordDto: WordDto, level: Int): Long? {
        val values = ContentValues().apply {
            put(MyDbWordsEN.Words.COLUMN_WORD, wordDto.word)
            put(MyDbWordsEN.Words.COLUMN_TRANSLATE, wordDto.translate)
            put(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION, wordDto.transcription)
            put(MyDbWordsEN.Words.COLUMN_LEVEL, level)
            put(MyDbWordsEN.Words.COLUMN_LANGUAGE, wordDto.language)
        }
        Log.i("Insert", values.toString())
        return db?.insert(MyDbWordsEN.Words.TABLE_NAME, null, values)
    }

    fun setLevelUser(uid: String, level: Int, language: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.UsersLanguages.ID_LEVEL, level)
        return db!!.update(
            MyDbWordsEN.UsersLanguages.TABLE_NAME,
            values,
            MyDbWordsEN.UsersLanguages.ID_USER + "= ? AND " + MyDbWordsEN.UsersLanguages.ID_LANGUAGE + "=?",
            arrayOf(uid, language.toString())
        )
    }

    fun setUserLanguage(uid: String, language: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.Users.COLUMN_LANGUAGE, language)
        return db!!.update(
            MyDbWordsEN.Users.TABLE_NAME,
            values, MyDbWordsEN.Users.COLUMN_UID + "= ?", arrayOf(uid)
        )
    }

    fun addUserLanguage(uid: String, language: Int) {
        val values = ContentValues().apply {
            put(MyDbWordsEN.UsersLanguages.ID_USER, uid)
            put(MyDbWordsEN.UsersLanguages.ID_LEVEL, 1)
            put(MyDbWordsEN.UsersLanguages.ID_LANGUAGE, language)
        }
        Log.i("Insert", values.toString())
        db?.insert(MyDbWordsEN.UsersLanguages.TABLE_NAME, null, values)
    }

    fun alreadyKnowWord(uid: String, idWord: Int, date: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.Dictionary.DATE, date)
        values.put(MyDbWordsEN.Dictionary.STATUS, MyDbWordsEN.Status.STUDIED)
        return db!!.update(
            MyDbWordsEN.Dictionary.TABLE_NAME,
            values,
            MyDbWordsEN.Dictionary.ID_USER + "= ? AND " + MyDbWordsEN.Dictionary.ID_WORDS + "=?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun setStatusWord(uid: String, idWord: Int, status: Int): Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.Dictionary.STATUS, status)
        return db!!.update(
            MyDbWordsEN.Dictionary.TABLE_NAME,
            values,
            MyDbWordsEN.Dictionary.ID_USER + "= ? AND " + MyDbWordsEN.Dictionary.ID_WORDS + "=?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun deleteNewAndBadStudiedWords(uid: String): Int {
        return db!!.delete(
            MyDbWordsEN.Dictionary.TABLE_NAME,
            MyDbWordsEN.Dictionary.ID_USER + "= ? AND (" + MyDbWordsEN.Dictionary.STATUS + " = ? OR " + MyDbWordsEN.Dictionary.STATUS + " = ?)",
            arrayOf(
                uid,
                MyDbWordsEN.Status.NEW.toString(),
                MyDbWordsEN.Status.BAD_STUDIED.toString()
            )
        )
    }

    fun deleteWordFromUser(uid: String, idWord: Int): Int {
        return db!!.delete(
            MyDbWordsEN.Dictionary.TABLE_NAME,
            MyDbWordsEN.Dictionary.ID_USER + "= ? AND " + MyDbWordsEN.Dictionary.ID_WORDS + "= ?",
            arrayOf(uid, idWord.toString())
        )
    }

    fun deleteWord(idWord: Int): Int {
        return db!!.delete(
            MyDbWordsEN.Words.TABLE_NAME,
            BaseColumns._ID + "= ?",
            arrayOf(idWord.toString())
        )
    }

    fun closeDb() {
        myDbHelper.close()
    }
}