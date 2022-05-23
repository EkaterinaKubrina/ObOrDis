package ru.ekaterinakubrina.wordsen.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.presenter.DictionaryPresenter
import java.util.*


class DictionaryActivity : AppCompatActivity(), DictionaryContractView {
    private val dictionaryPresenter = DictionaryPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        dictionaryPresenter.getDictionary(uid)

        val layout: ConstraintLayout = findViewById(R.id.ConLay1)
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView2)
        val textTranscription: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView3)


        val addWord: Button = findViewById(R.id.add_word)
        addWord.setOnClickListener {
            layout.visibility = View.VISIBLE
        }

        val closeButton: FloatingActionButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            layout.visibility = View.INVISIBLE
        }

        val addWord2: Button = findViewById(R.id.add_word2)
        addWord2.setOnClickListener {
            dictionaryPresenter.addUsersWord(
                uid,
                textWord.text.toString(),
                textTranslate.text.toString(),
                textTranscription.text.toString()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun showWords(list: ArrayList<String>) {
        val layout: LinearLayout = findViewById(R.id.content)
        list.sort()
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

    override fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun underlineRedWord() {
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        textWord.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

    override fun underlineRedTranslate() {
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView2)
        textTranslate.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }


    override fun clearTextView() {
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView2)
        val textTranscription: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView3)
        textWord.text.clear()
        textTranslate.text.clear()
        textTranscription.text.clear()
    }

    override fun removeUnderline() {
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView2)
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        textWord.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.black)
        textTranslate.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.black)
    }

}