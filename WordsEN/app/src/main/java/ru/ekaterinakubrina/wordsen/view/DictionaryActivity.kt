package ru.ekaterinakubrina.wordsen.view

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter


class DictionaryActivity : AppCompatActivity() {
    private val userPresenter = UserPresenter(UserDaoImpl(this))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        val layout: LinearLayout = findViewById(R.id.content)
        val id: String = intent.getSerializableExtra("ID_USER") as String

        val list = userPresenter.getDictionary(id)
        list.forEach {
            val text = TextView(this)
            text.textSize = 18F
            text.setTextColor(getColor(R.color.black))
            text.setPadding(10, 10, 10, 10)
            val face = ResourcesCompat.getFont(this, R.font.tenor_sans)
            text.typeface = face
            text.text = it
            layout.addView(text)
        }

    }
}