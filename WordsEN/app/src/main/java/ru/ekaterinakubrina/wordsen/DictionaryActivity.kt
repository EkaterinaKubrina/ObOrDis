package ru.ekaterinakubrina.wordsen

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import ru.ekaterinakubrina.wordsen.data.MyDbManager


class DictionaryActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        val layout: LinearLayout = findViewById(R.id.content)
        val id : Int = intent.getSerializableExtra("ID_USER") as Int

        myDbManager.openDb()
        val list = myDbManager.getUserWords(id)
        list.forEach{
            val text = TextView(this)
            text.textSize = 22F
            text.setTextColor(resources.getColor( R.color.black))
            text.setPadding(0, 10, 0, 10)
            val face = ResourcesCompat.getFont(this, R.font.tenor_sans)
            text.typeface = face
            text.text = it
            layout.addView(text)
        }

    }
}