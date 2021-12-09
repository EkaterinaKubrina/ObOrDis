package ru.ekaterinakubrina.wordsen.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context : Context):SQLiteOpenHelper(context, MyDbWordsEN.DATABASE_NAME,
        null, MyDbWordsEN.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDbWordsEN.Users.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Words.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.UsersWords.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyDbWordsEN.Users.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Words.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.UsersWords.SQL_DELETE_TABLE)
        onCreate(db)
    }
}