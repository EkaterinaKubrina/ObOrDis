package ru.ekaterinakubrina.wordsen.data

import android.provider.BaseColumns

object MyDbWordsEN {

    const val DATABASE_NAME = "wordsEN.db"

    const val DATABASE_VERSION = 1

    object Users : BaseColumns {
        const val TABLE_NAME = "users"

        const val COLUMN_UID = "uid"
        const val COLUMN_NAME = "userName"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_LEVEL = "level"

        const val LEVEL_A0 = 1
        const val LEVEL_A1 = 2
        const val LEVEL_A2 = 3
        const val LEVEL_B1 = 4
        const val LEVEL_B2 = 5
        const val LEVEL_C1 = 6

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_UID + " TEXT PRIMARY KEY NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_EMAIL + " TEXT NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_LEVEL + " INTEGER, " +
                "UNIQUE ($COLUMN_EMAIL));")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Words : BaseColumns {
        const val TABLE_NAME = "words"

        const val COLUMN_WORD = "word"
        const val COLUMN_TRANSLATE = "translate"
        const val COLUMN_TRANSCRIPTION = "transcription"
        const val COLUMN_LEVEL = "level"

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORD + " TEXT NOT NULL, "
                + COLUMN_TRANSLATE + " TEXT NOT NULL, "
                + COLUMN_TRANSCRIPTION + " TEXT NOT NULL, "
                + COLUMN_LEVEL + "  INTEGER NOT NULL, " +
                "UNIQUE ($COLUMN_WORD));")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Users.TABLE_NAME}"

    }

    object Dictionary : BaseColumns {
        const val TABLE2_NAME = "users_words"

        const val ID_USER = "idUser"
        const val ID_WORDS = "idWord"
        const val DATE = "date"
        const val STATUS = "status"

        const val NEW = 0
        const val STUDIED = 1
        const val BAD_STUDIED = 2


        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE2_NAME + " ("
                + ID_USER + " TEXT NOT NULL, "
                + ID_WORDS + " INTEGER NOT NULL, "
                + DATE + " INTEGER NOT NULL, "
                + STATUS + " INTEGER NOT NULL);")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Users.TABLE_NAME}"

    }


}