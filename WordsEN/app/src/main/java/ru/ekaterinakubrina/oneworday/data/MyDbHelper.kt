package ru.ekaterinakubrina.oneworday.data

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import ru.ekaterinakubrina.oneworday.R
import java.io.IOException


class MyDbHelper(context: Context) : SQLiteOpenHelper(
    context, MyDbWordsEN.DATABASE_NAME,
    null, MyDbWordsEN.DATABASE_VERSION
) {
    private val fContext: Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDbWordsEN.Users.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Words.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Dictionary.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.UsersLanguages.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Status.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Level.SQL_CREATE_TABLE)
        db?.execSQL(MyDbWordsEN.Language.SQL_CREATE_TABLE)
        addEnumStatus(db)
        addEnumLevel(db)
        addEnumLanguage(db)

        // Добавляем записи в таблицу
        val values = ContentValues()
        // Получим файл из ресурсов
        val res: Resources = fContext.resources
        // Открываем xml-файл
        val fxml: XmlResourceParser = res.getXml(R.xml.words_records)
        try {
            // Ищем конец документа
            var eventType = fxml.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Ищем теги record
                if (eventType == XmlPullParser.START_TAG
                    && fxml.name == "record"
                ) {
                    // Тег Record найден, теперь получим его атрибуты и
                    // вставляем в таблицу
                    val word = fxml.getAttributeValue(4)
                    val translate = fxml.getAttributeValue(3)
                    val transcription = fxml.getAttributeValue(2)
                    val level = fxml.getAttributeValue(1)
                    val language = fxml.getAttributeValue(0)
                    values.put("word", word)
                    values.put("translate", translate)
                    values.put("transcription", transcription)
                    values.put("level", level)
                    values.put("language", language)
                    db!!.insert(MyDbWordsEN.Words.TABLE_NAME, null, values)
                }
                eventType = fxml.next()
            }
        } catch (e: XmlPullParserException) {
            Log.e("Test", e.message, e)
        } catch (e: IOException) {
            Log.e("Test", e.message, e)
        } finally {
            fxml.close()
        }

        val values1 = ContentValues()
        val fxml1: XmlResourceParser = res.getXml(R.xml.test_user_record)
        try {
            var eventType1 = fxml1.eventType
            while (eventType1 != XmlPullParser.END_DOCUMENT) {
                if (eventType1 == XmlPullParser.START_TAG
                    && fxml1.name == "record"
                ) {
                    val uid = fxml1.getAttributeValue(1)
                    val userName = fxml1.getAttributeValue(2)
                    val language = fxml1.getAttributeValue(0)
                    values1.put("uid", uid)
                    values1.put("userName", userName)
                    values1.put("language", language)
                    db!!.insert(MyDbWordsEN.Users.TABLE_NAME, null, values1)
                }
                eventType1 = fxml1.next()
            }
        } catch (e: XmlPullParserException) {
            Log.e("Test", e.message, e)
        } catch (e: IOException) {
            Log.e("Test", e.message, e)
        } finally {
            fxml1.close()
        }

    }

    private fun addEnumStatus(db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put("status", "NEW")
        values.put("id", MyDbWordsEN.Status.NEW)
        db!!.insert(MyDbWordsEN.Status.TABLE_NAME, null, values)
        values.put("status", "STUDIED")
        values.put("id", MyDbWordsEN.Status.STUDIED)
        db.insert(MyDbWordsEN.Status.TABLE_NAME, null, values)
        values.put("status", "BAD_STUDIED")
        values.put("id", MyDbWordsEN.Status.BAD_STUDIED)
        db.insert(MyDbWordsEN.Status.TABLE_NAME, null, values)

    }

    private fun addEnumLevel(db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put("level", "A0")
        values.put("id", MyDbWordsEN.Level.LEVEL_A0)
        db!!.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
        values.put("level", "A1")
        values.put("id", MyDbWordsEN.Level.LEVEL_A1)
        db.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
        values.put("level", "A2")
        values.put("id", MyDbWordsEN.Level.LEVEL_A2)
        db.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
        values.put("level", "B1")
        values.put("id", MyDbWordsEN.Level.LEVEL_B1)
        db.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
        values.put("level", "B2")
        values.put("id", MyDbWordsEN.Level.LEVEL_B2)
        db.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
        values.put("level", "C1")
        values.put("id", MyDbWordsEN.Level.LEVEL_C1)
        db.insert(MyDbWordsEN.Level.TABLE_NAME, null, values)
    }


    private fun addEnumLanguage(db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put("language", "Английский")
        values.put("id", MyDbWordsEN.Language.ENGLISH)
        db!!.insert(MyDbWordsEN.Language.TABLE_NAME, null, values)
        values.put("language", "Французский")
        values.put("id", MyDbWordsEN.Language.FRENCH)
        db.insert(MyDbWordsEN.Language.TABLE_NAME, null, values)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyDbWordsEN.Users.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Words.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Dictionary.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.UsersLanguages.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Status.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Level.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Language.SQL_DELETE_TABLE)
        onCreate(db)
    }
}
