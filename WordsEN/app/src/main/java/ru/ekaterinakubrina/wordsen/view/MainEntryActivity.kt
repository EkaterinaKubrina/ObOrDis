package ru.ekaterinakubrina.wordsen.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.notify.NotifyService
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.dto.WordDto
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter
import ru.ekaterinakubrina.wordsen.presenter.WordPresenter
import java.text.SimpleDateFormat
import java.util.*


class MainEntryActivity : AppCompatActivity() {
    private val userPresenter = UserPresenter(UserDaoImpl(this))
    private val wordPresenter = WordPresenter(WordDaoImpl(this))
    private var tts: TextToSpeech? = null
    private var ttsEnabled = false
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_entry)

        val idUser: String = intent.getSerializableExtra("ID_USER") as String
        val levelUser: Int = userPresenter.getLevelUser(idUser)!!

        val dictionary: Button = findViewById(R.id.button4)
        val tests: Button = findViewById(R.id.button3)
        val alreadyKnow: Button = findViewById(R.id.button7)
        val name: Button = findViewById(R.id.main_name)
        val level: TextView = findViewById(R.id.main_level)
        val wordEn: TextView = findViewById(R.id.main_word)
        val sound: ImageView = findViewById(R.id.imageView10)
        val soundSlow: ImageView = findViewById(R.id.imageView12)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result: Int? = tts?.setLanguage(Locale.ENGLISH)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    println("Language not supported")
                } else {
                    ttsEnabled = true // flag tts as initialized
                }
            } else {
                println("Failed $status")
            }
        }

        name.text = userPresenter.getNameUser(idUser)
        when (levelUser) {
            MyDbWordsEN.Users.LEVEL_A0 -> level.text = "A0"
            MyDbWordsEN.Users.LEVEL_A1 -> level.text = "A1"
            MyDbWordsEN.Users.LEVEL_A2 -> level.text = "A2"
            MyDbWordsEN.Users.LEVEL_B1 -> level.text = "B1"
            MyDbWordsEN.Users.LEVEL_B2 -> level.text = "B2"
            MyDbWordsEN.Users.LEVEL_C1 -> level.text = "C1"
        }

        val lastDate = wordPresenter.getLastDateAddedWord(idUser)
        if (lastDate?.equals(currentDate) == false) {
            setWord(wordPresenter.addWord(idUser, levelUser))
        } else {
            setWord(wordPresenter.getWordByDate(idUser, currentDate))
        }


        dictionary.setOnClickListener {
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("ID_USER", idUser)
            startActivity(intent)
        }

        alreadyKnow.setOnClickListener {
            wordPresenter.alreadyKnowWord(
                idUser,
                wordPresenter.getWordByDate(idUser, currentDate).wordId,
                currentDate - 1,
                MyDbWordsEN.UsersWords.STUDIED
            )
            setWord(wordPresenter.addWord(idUser, levelUser))
        }

        sound.setOnClickListener {
            if (ttsEnabled) {
                tts?.setSpeechRate(0.8F)
                speakOut(wordEn.text.toString())
            }
        }

        soundSlow.setOnClickListener {
            if (ttsEnabled) {
                tts?.setSpeechRate(0.2F)
                speakOut(wordEn.text.toString())
            }
        }

        tests.setOnClickListener {
            val intent = Intent(this, SelectTestActivity::class.java)
            intent.putExtra("ID_USER", idUser)
            startActivity(intent)
        }

        name.setOnClickListener(viewClickListener)
    }


    private fun speakOut(text: String) {
        if (!ttsEnabled) {
            return
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun setWord(newWordDto: WordDto) {
        val word: TextView = findViewById(R.id.main_word)
        val transcription: TextView = findViewById(R.id.transcription)
        val translate: TextView = findViewById(R.id.translate)

        word.text = newWordDto.word
        transcription.text = newWordDto.transcription
        translate.text = newWordDto.translate
    }

    private var viewClickListener: View.OnClickListener =
        View.OnClickListener { v -> showPopupMenu(v) }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu
            .setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu1 -> {
                        changeLevelEnable(popupMenu)
                        true
                    }
                    R.id.menu2 -> {
                        alertDialogExit()
                        true
                    }
                    R.id.A0 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_A0)
                        true
                    }
                    R.id.A1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_A1)
                        true
                    }
                    R.id.A2 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_A2)
                        true
                    }
                    R.id.B1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_B1)
                        true
                    }
                    R.id.B2 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_B2)
                        true
                    }
                    R.id.C1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Users.LEVEL_C1)
                        true
                    }
                    else -> false
                }
            }

        popupMenu.show()
    }

    private fun changeLevelEnable(popupMenu: PopupMenu) {
        val level: TextView = findViewById(R.id.main_level)
        when (level.text) {
            "A0" -> popupMenu.menu.findItem(R.id.A0).isEnabled = false
            "A1" -> popupMenu.menu.findItem(R.id.A1).isEnabled = false
            "A2" -> popupMenu.menu.findItem(R.id.A2).isEnabled = false
            "B1" -> popupMenu.menu.findItem(R.id.B1).isEnabled = false
            "B2" -> popupMenu.menu.findItem(R.id.B2).isEnabled = false
            "C1" -> popupMenu.menu.findItem(R.id.C1).isEnabled = false
        }
    }

    private fun alertDialogExit() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Вы уверены что хотите выйти?")
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "Да",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                val idUser: String = intent.getSerializableExtra("ID_USER") as String
                val levelUser: Int = userPresenter.getLevelUser(idUser)!!
                val intent = Intent(this, NotifyService::class.java)
                intent.putExtra("START_FLAG", false)
                intent.putExtra("ID_USER", idUser)
                intent.putExtra("LEVEL_USER", levelUser)
                startService(intent)
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
            })
        )
        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "Отменить",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                alertDialog.cancel()
            })
        )
        alertDialog.show()
    }

    private fun alertDialogSetLevel(level: Int) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Вы уверены что хотите изменить уровень?")
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "Да",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                val id: String = intent.getSerializableExtra("ID_USER") as String
                userPresenter.setLevel(id, level)
                wordPresenter.deleteNewAndBadStudiedWords(
                    id
                )
                val intent = Intent(this, MainEntryActivity::class.java)
                intent.putExtra("ID_USER", id)
                intent.putExtra("LEVEL_USER", level)
                startActivity(intent)
            })
        )
        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "Отменить",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                alertDialog.cancel()
            })
        )
        alertDialog.show()
    }


}




