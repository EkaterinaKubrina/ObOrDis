package ru.ekaterinakubrina.wordsen.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb () {
        db = myDbHelper.writableDatabase
    }

    fun insertUser(name: String, email: String, password: String) : Long? {
        val values = ContentValues().apply {
            put(MyDbWordsEN.Users.COLUMN_NAME, name)
            put(MyDbWordsEN.Users.COLUMN_EMAIL, email)
            put(MyDbWordsEN.Users.COLUMN_PASSWORD, password)
            put(MyDbWordsEN.Users.COLUMN_LEVEL, 0)
        }
        return db?.insert(MyDbWordsEN.Users.TABLE_NAME, null, values)
    }

    fun insertWordToUser(id: Int, wordId: Int, date: Int) : Long? {
        val values = ContentValues().apply {
            put(MyDbWordsEN.UsersWords.ID_USER, id)
            put(MyDbWordsEN.UsersWords.ID_WORDS, wordId)
            put(MyDbWordsEN.UsersWords.DATE, date)
        }
        return db?.insert(MyDbWordsEN.UsersWords.TABLE2_NAME, null, values)
    }

    fun getUserId(email: String, password: String) : String? {
        val projection = arrayOf<String>(
            BaseColumns._ID
        )
        var id : String? = null
        val selection: String = MyDbWordsEN.Users.COLUMN_EMAIL + "=? AND " + MyDbWordsEN.Users.COLUMN_PASSWORD + "=?"
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
        while(cursor?.moveToNext()!!) {
            id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID))
        }
        cursor.close()
        return id
    }



    fun getUserName(id: Int) : String? {
        val projection = arrayOf(
            MyDbWordsEN.Users.COLUMN_NAME
        )
        var name : String? = null
        val selection: String = BaseColumns._ID + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db?.query(
            MyDbWordsEN.Users.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while(cursor?.moveToNext()!!) {
            name = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Users.COLUMN_NAME))
        }
        cursor.close()
        return name
    }


    fun getLevelUser(id: String) : Int? {
        val projection = arrayOf(
                MyDbWordsEN.Users.COLUMN_LEVEL
        )
        var level : Int? = null
        val selection: String = BaseColumns._ID + "=?"
        val selectionArgs = arrayOf(id)
        val cursor = db?.query(
                MyDbWordsEN.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )
        while(cursor?.moveToNext()!!) {
            level = cursor.getInt(cursor.getColumnIndex(MyDbWordsEN.Users.COLUMN_LEVEL))
        }
        cursor.close()
        return level
    }

    fun getLastDate(id: String) : Int? {
        var date : Int? = null
        val query = ("SELECT " + "MAX(" + MyDbWordsEN.UsersWords.DATE + ")"
                + " FROM " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                 " WHERE "  +
                MyDbWordsEN.UsersWords.ID_USER + " = " + id)
        val cursor = db?.rawQuery(query, null)
        while(cursor?.moveToNext()!!) {
            date = cursor.getInt(cursor.getColumnIndex("MAX("+MyDbWordsEN.UsersWords.DATE+ ")"))
        }
        cursor.close()
        return date
    }

    fun getUserWords(id: Int) : ArrayList<String> {
        var word: String
        var translate: String
        val list = ArrayList<String>()
        val query = ("SELECT " + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "."+ BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE "  +
                MyDbWordsEN.UsersWords.ID_USER + " = " + id.toString())
        val cursor = db?.rawQuery(query, null)

        while(cursor?.moveToNext()!!) {
            word = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_WORD))
            translate = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            list.add("$word - $translate")
        }
        cursor.close()
        return list
    }

    fun getWord(id: Int, level: Int) : Word {
        var word = ""
        var transcription = ""
        var translate = ""
        var idW = 0
        val query = ("SELECT " + BaseColumns._ID + ", "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION+ ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "."+ BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " + MyDbWordsEN.Words.COLUMN_LEVEL + " = " + level.toString() + " AND (" +
                MyDbWordsEN.UsersWords.ID_USER + " != " + id.toString() + " OR " + MyDbWordsEN.UsersWords.ID_USER + " IS NULL)")
        val cursor = db?.rawQuery(query, null)

        while(cursor?.moveToNext()!!) {
            idW = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            word = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_WORD))
            transcription = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION))
            translate = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            break
        }
        cursor.close()
        return Word(idW, word, transcription, translate)
    }

    fun getWordByDate(id: Int, date: Int) : Word {
        var word = ""
        var transcription = ""
        var translate = ""
        val query = ("SELECT "
                + MyDbWordsEN.Words.COLUMN_WORD + ", "
                + MyDbWordsEN.Words.COLUMN_TRANSCRIPTION+ ", "
                + MyDbWordsEN.Words.COLUMN_TRANSLATE + " FROM " + MyDbWordsEN.Words.TABLE_NAME +
                " LEFT OUTER JOIN " + MyDbWordsEN.UsersWords.TABLE2_NAME +
                " ON " + MyDbWordsEN.Words.TABLE_NAME + "."+ BaseColumns._ID + " = " + MyDbWordsEN.UsersWords.TABLE2_NAME + "." +
                MyDbWordsEN.UsersWords.ID_WORDS + " WHERE " + MyDbWordsEN.UsersWords.DATE + " = " + date.toString() + " AND " +
                MyDbWordsEN.UsersWords.ID_USER + " = " + id.toString() )
        val cursor = db?.rawQuery(query, null)

        while(cursor?.moveToNext()!!) {
            word = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_WORD))
            transcription = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_TRANSCRIPTION))
            translate = cursor.getString(cursor.getColumnIndex(MyDbWordsEN.Words.COLUMN_TRANSLATE))
            break
        }
        cursor.close()
        return Word(word, transcription, translate)
    }

    fun setLevelUser(id: Int, level: Int) : Int {
        val values = ContentValues()
        values.put(MyDbWordsEN.Users.COLUMN_LEVEL, level)
        return db!!.update(
            MyDbWordsEN.Users.TABLE_NAME,
            values, BaseColumns._ID + "= ?", arrayOf(id.toString())
        )
    }

    fun closeDb() {
        myDbHelper.close()
    }
}