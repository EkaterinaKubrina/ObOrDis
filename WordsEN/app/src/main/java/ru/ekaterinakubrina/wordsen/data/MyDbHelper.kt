package ru.ekaterinakubrina.wordsen.data

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import ru.ekaterinakubrina.wordsen.R
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
                    val word = fxml.getAttributeValue(3)
                    val translate = fxml.getAttributeValue(2)
                    val transcription = fxml.getAttributeValue(1)
                    val level = fxml.getAttributeValue(0)
                    values.put("word", word)
                    values.put("translate", translate)
                    values.put("transcription", transcription)
                    values.put("level", level)
                    db!!.insert(MyDbWordsEN.Words.TABLE_NAME, null, values)
                }
                eventType = fxml.next()
            }
        } // Catch errors
        catch (e: XmlPullParserException) {
            Log.e("Test", e.message, e)
        } catch (e: IOException) {
            Log.e("Test", e.message, e)
        } finally {
            // Close the xml file
            fxml.close()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyDbWordsEN.Users.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Words.SQL_DELETE_TABLE)
        db?.execSQL(MyDbWordsEN.Dictionary.SQL_DELETE_TABLE)
        onCreate(db)
    }
}