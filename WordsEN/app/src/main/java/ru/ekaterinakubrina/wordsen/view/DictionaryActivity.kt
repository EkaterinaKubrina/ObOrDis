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
import ru.ekaterinakubrina.wordsen.contracts.DictionaryContract
import ru.ekaterinakubrina.wordsen.presenter.DictionaryPresenter
import java.util.*


open class DictionaryActivity : AppCompatActivity(), DictionaryContract.View {
    private val dictionaryPresenter : DictionaryContract.Presenter = DictionaryPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        dictionaryPresenter.getDictionary(uid)

        val layout: ConstraintLayout = findViewById(R.id.layoutAddMyWord)
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewWord)
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranslate)
        val textTranscription: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranscription)


        val addWord: Button = findViewById(R.id.buttonAddMyWord)
        addWord.setOnClickListener {
            layout.visibility = View.VISIBLE
        }

        val closeButton: FloatingActionButton = findViewById(R.id.buttonCloseAddMyWord)
        closeButton.setOnClickListener {
            layout.visibility = View.INVISIBLE
        }

        val addWord2: Button = findViewById(R.id.buttonAddMyWord1)
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
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewWord)
        textWord.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }

    override fun underlineRedTranslate() {
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranslate)
        textTranslate.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.myRed)
    }


    override fun clearTextView() {
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewWord)
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranslate)
        val textTranscription: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranscription)
        textWord.text.clear()
        textTranslate.text.clear()
        textTranscription.text.clear()
    }

    override fun removeUnderline() {
        val textTranslate: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewTranslate)
        val textWord: AutoCompleteTextView = findViewById(R.id.autoCompleteTextViewWord)
        textWord.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.black)
        textTranslate.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.black)
    }

}