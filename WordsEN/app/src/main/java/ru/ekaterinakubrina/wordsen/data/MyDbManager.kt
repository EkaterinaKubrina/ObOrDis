package ru.ekaterinakubrina.wordsen.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

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
        return db?.insert(MyDbWordsEN.Users.TABLE_NAME, null, values);
    }

    fun getUserId(email: String, password: String) : String? {
        val projection = arrayOf<String>(
                BaseColumns._ID)
        var id : String? = null
        val selection: String = MyDbWordsEN.Users.COLUMN_EMAIL + "=? AND " + MyDbWordsEN.Users.COLUMN_PASSWORD + "=?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db?.query(MyDbWordsEN.Users.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        while(cursor?.moveToNext()!!) {
            id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID))
        }
        cursor.close()
        return id
    }


    fun getLevelUser(id: Int) : Int? {
        val projection = arrayOf<String>(
                MyDbWordsEN.Users.COLUMN_LEVEL)
        var level : Int? = null
        val selection: String = BaseColumns._ID + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db?.query(MyDbWordsEN.Users.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        while(cursor?.moveToNext()!!) {
            level = cursor.getInt(cursor.getColumnIndex(MyDbWordsEN.Users.COLUMN_LEVEL))
        }
        cursor.close()
        return level
    }

    fun closeDb() {
        myDbHelper.close()
    }
}