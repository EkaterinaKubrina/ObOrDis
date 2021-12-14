package ru.ekaterinakubrina.wordsen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import ru.ekaterinakubrina.wordsen.data.Word
import java.text.SimpleDateFormat
import java.util.*

class MainEntryActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_entry)
        val id : Int? = intent.getSerializableExtra("ID_USER") as Int?
        val lvl : Int? = intent.getSerializableExtra("LEVEL_USER") as Int?

        val dict: Button = findViewById(R.id.button4)
        val name: Button = findViewById(R.id.main_name)
        val level: TextView = findViewById(R.id.main_level)
        val word: TextView = findViewById(R.id.main_word)
        val transcription: TextView = findViewById(R.id.transcription)
        val translate: TextView = findViewById(R.id.translate)


        myDbManager.openDb()
        name.text = id?.let { myDbManager.getUserName(it) }
        when (lvl) {
            1 -> level.text = "A0"
            2 -> level.text = "A1"
            3 -> level.text = "A2"
            4 -> level.text = "B1"
            5 -> level.text = "B2"
            6 -> level.text = "C1"
        }


        val sdf = SimpleDateFormat("yyyyddMM", Locale.ENGLISH)
        val currentDate = sdf.format(Date()).toInt()

        val lastDate = myDbManager.getLastDate(id.toString())

        if(lastDate?.equals(currentDate) == false){
            id?.let { lvl?.let { it1 -> addWord(it, it1, word, transcription, translate) } }
        }
        else{
            val w = id?.let { myDbManager.getWordByDate(it, currentDate) }
            word.text = w?.word
            transcription.text = w?.transcription
            translate.text = w?.translate
        }

        dict.setOnClickListener {
            val intent = Intent(this@MainEntryActivity, DictionaryActivity::class.java)
            intent.putExtra("ID_USER", id)
            startActivity(intent)
        }
    }

    private fun addWord(id:Int, lvl:Int, word:TextView, transcription:TextView, translate:TextView, ){
        val w : Word = myDbManager.getWord(id, lvl)
        word.text = w.word
        transcription.text = w.transcription
        translate.text = w.translate
        val sdf = SimpleDateFormat("yyyyddMM", Locale.ENGLISH)
        val currentDate = sdf.format(Date())
        id.let {
            w.wordId.let { it1 -> myDbManager.insertWordToUser(it, it1, currentDate.toInt()) }
        }
    }
}