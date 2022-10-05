package ru.ekaterinakubrina.oneworday.data

import android.provider.BaseColumns

object MyDbWordsEN {

    const val DATABASE_NAME = "wordsEN.db"

    const val DATABASE_VERSION = 1

    object Users : BaseColumns {
        const val TABLE_NAME = "users"

        const val COLUMN_UID = "uid"
        const val COLUMN_NAME = "userName"
        const val COLUMN_LANGUAGE = "language"

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_UID + " TEXT PRIMARY KEY NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_LANGUAGE + " INTEGER NOT NULL DEFAULT (0), " +
                "FOREIGN KEY($COLUMN_LANGUAGE) REFERENCES language(id), " +
                "UNIQUE ($COLUMN_UID));")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Words : BaseColumns {
        const val TABLE_NAME = "words"

        const val COLUMN_WORD = "word"
        const val COLUMN_TRANSLATE = "translate"
        const val COLUMN_TRANSCRIPTION = "transcription"
        const val COLUMN_LEVEL = "level"
        const val COLUMN_LANGUAGE = "language"

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORD + " TEXT NOT NULL, "
                + COLUMN_TRANSLATE + " TEXT NOT NULL, "
                + COLUMN_TRANSCRIPTION + " TEXT NOT NULL, "
                + COLUMN_LEVEL + "  INTEGER NOT NULL, "
                + COLUMN_LANGUAGE + " INTEGER NOT NULL DEFAULT (0), " +
                "FOREIGN KEY($COLUMN_LANGUAGE) REFERENCES languages(id) ON DELETE CASCADE, " +
                "UNIQUE ($COLUMN_WORD));")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME}"
    }

    object Dictionary : BaseColumns {
        const val TABLE_NAME = "users_words"

        const val ID_USER = "idUser"
        const val ID_WORDS = "idWord"
        const val DATE = "date"
        const val STATUS = "status"

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_USER + " TEXT KEY NOT NULL, "
                + ID_WORDS + " INTEGER KEY  NOT NULL, "
                + DATE + " INTEGER NOT NULL, "
                + STATUS + " INTEGER NOT NULL, " +
                "FOREIGN KEY($STATUS) REFERENCES status_words(id));")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object UsersLanguages : BaseColumns {
        const val TABLE_NAME = "users_languages"

        const val ID_USER = "idUser"
        const val ID_LANGUAGE = "idLanguage"
        const val ID_LEVEL = "idLevel"

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_USER + " TEXT KEY NOT NULL, "
                + ID_LANGUAGE + " INTEGER KEY  NOT NULL, "
                + ID_LEVEL + " INTEGER NOT NULL, " +
                "FOREIGN KEY($ID_USER) REFERENCES ${Users.TABLE_NAME}(${Users.COLUMN_UID}) ON DELETE CASCADE, " +
                "FOREIGN KEY($ID_LANGUAGE) REFERENCES ${Language.TABLE_NAME}(${Language.ID_LANGUAGE}) ON DELETE CASCADE, " +
                "FOREIGN KEY($ID_LEVEL) REFERENCES ${Level.TABLE_NAME}(${Level.ID_LEVEL}) ON DELETE CASCADE);")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


    object Status : BaseColumns {
        const val TABLE_NAME = "status_words"

        const val ID_STATUS = "id"
        const val STATUS = "status"

        const val NEW = 0
        const val STUDIED = 1
        const val BAD_STUDIED = 2

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_STATUS + " INTEGER PRIMARY KEY NOT NULL, "
                + STATUS + " TEXT NOT NULL );")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


    object Level : BaseColumns {
        const val TABLE_NAME = "levels"

        const val ID_LEVEL = "id"
        const val LEVEL = "level"

        const val LEVEL_A0 = 1
        const val LEVEL_A1 = 2
        const val LEVEL_A2 = 3
        const val LEVEL_B1 = 4
        const val LEVEL_B2 = 5
        const val LEVEL_C1 = 6

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_LEVEL + " INTEGER PRIMARY KEY NOT NULL, "
                + LEVEL + " TEXT NOT NULL );")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


    object Language : BaseColumns {
        const val TABLE_NAME = "languages"

        const val ID_LANGUAGE = "id"
        const val LANGUAGE = "language"

        const val ENGLISH = 0
        const val FRENCH = 1

        var SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_LANGUAGE + " INTEGER PRIMARY KEY NOT NULL, "
                + LANGUAGE + " TEXT NOT NULL );")
        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


}