package ru.ekaterinakubrina.oneworday.mvp.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.oneworday.R
import ru.ekaterinakubrina.oneworday.data.MyDbWordsEN
import ru.ekaterinakubrina.oneworday.dto.UserDto
import ru.ekaterinakubrina.oneworday.dto.WordDto
import ru.ekaterinakubrina.oneworday.mvp.contracts.MainEntryContract
import ru.ekaterinakubrina.oneworday.mvp.presenter.MainEntryPresenter
import ru.ekaterinakubrina.oneworday.notify.NotifyService
import java.util.*


class MainEntryActivity : AppCompatActivity(), MainEntryContract.View {
    private val mainEntryPresenter: MainEntryContract.Presenter = MainEntryPresenter(this, this)
    private var tts: TextToSpeech? = null
    private var ttsEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_entry)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        val wordEn: TextView = findViewById(R.id.main_word)
        mainEntryPresenter.loadData(uid)

        val dictionary: Button = findViewById(R.id.buttonDictionary)
        dictionary.setOnClickListener {
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("ID_USER", uid)
            startActivity(intent)
        }

        val alreadyKnow: Button = findViewById(R.id.buttonAlreadyKnowWord)
        alreadyKnow.setOnClickListener {
            mainEntryPresenter.clickOnAlreadyKnowWordButton(uid)
        }

        val sound: ImageView = findViewById(R.id.imageViewSound)
        sound.setOnClickListener {
            if (ttsEnabled) {
                tts?.setSpeechRate(0.8F)
                speakOut(wordEn.text.toString())
            }
        }

        val soundSlow: ImageView = findViewById(R.id.imageViewSoundSlow)
        soundSlow.setOnClickListener {
            if (ttsEnabled) {
                tts?.setSpeechRate(0.2F)
                speakOut(wordEn.text.toString())
            }
        }

        val tests: Button = findViewById(R.id.buttonTests)
        tests.setOnClickListener {
            val intent = Intent(this, SelectTestActivity::class.java)
            intent.putExtra("ID_USER", uid)
            startActivity(intent)
        }

        val name: Button = findViewById(R.id.main_name)
        name.setOnClickListener(viewClickListener)
    }

    override fun setWord(newWordDto: WordDto) {
        val word: TextView = findViewById(R.id.main_word)
        val transcription: TextView = findViewById(R.id.transcription)
        val translate: TextView = findViewById(R.id.translate)

        word.text = newWordDto.word
        transcription.text = newWordDto.transcription
        translate.text = newWordDto.translate
    }

    override fun initTextToSpeech(language: Int) {
        var locale: Locale = Locale.ENGLISH
        if (language == MyDbWordsEN.Language.ENGLISH) {
            locale = Locale.ENGLISH
        } else if (language == MyDbWordsEN.Language.FRENCH) {
            locale = Locale.FRENCH
        }

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result: Int? = tts?.setLanguage(locale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    println("Language not supported")
                } else {
                    ttsEnabled = true // flag tts as initialized
                }
            } else {
                println("Failed $status")
            }
        }
    }

    override fun setName(newNameUser: String) {
        val name: Button = findViewById(R.id.main_name)
        name.text = newNameUser
    }

    override fun newLevel() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Поздравляем! Вы перешли на новый уровень")
        alertDialog.setButton(
            DialogInterface.BUTTON_NEUTRAL,
            "Круто!",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                alertDialog.cancel()
            })
        )
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun setLevel(levelUser: Int) {
        val level: TextView = findViewById(R.id.main_level)
        when (levelUser) {
            MyDbWordsEN.Level.LEVEL_A0 -> level.text = "A0"
            MyDbWordsEN.Level.LEVEL_A1 -> level.text = "A1"
            MyDbWordsEN.Level.LEVEL_A2 -> level.text = "A2"
            MyDbWordsEN.Level.LEVEL_B1 -> level.text = "B1"
            MyDbWordsEN.Level.LEVEL_B2 -> level.text = "B2"
            MyDbWordsEN.Level.LEVEL_C1 -> level.text = "C1"
        }
    }

    override fun changeLanguageEnable(language: Int,  pop: PopupMenu) {
        when (language) {
            MyDbWordsEN.Language.ENGLISH -> pop.menu.findItem(R.id.english).isEnabled = false
            MyDbWordsEN.Language.FRENCH -> pop.menu.findItem(R.id.french).isEnabled = false
        }
    }

    override fun getName(): String {
        val name: TextView = findViewById(R.id.main_name)
        return name.text.toString()
    }

    private fun speakOut(text: String) {
        if (!ttsEnabled) {
            return
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
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
                    R.id.menu3 -> {
                        val idUser: String = intent.getSerializableExtra("ID_USER") as String
                        mainEntryPresenter.enableLanguage(idUser, popupMenu)
                        true
                    }
                    R.id.A0 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_A0)
                        true
                    }
                    R.id.A1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_A1)
                        true
                    }
                    R.id.A2 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_A2)
                        true
                    }
                    R.id.B1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_B1)
                        true
                    }
                    R.id.B2 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_B2)
                        true
                    }
                    R.id.C1 -> {
                        alertDialogSetLevel(MyDbWordsEN.Level.LEVEL_C1)
                        true
                    }
                    R.id.english -> {
                        alertDialogSetLanguage(MyDbWordsEN.Language.ENGLISH)
                        true
                    }
                    R.id.french -> {
                        alertDialogSetLanguage(MyDbWordsEN.Language.FRENCH)
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
                val levelUser: Int = mainEntryPresenter.getLevelUser(idUser)!!
                val intent = Intent(this, NotifyService::class.java)
                intent.putExtra("START_FLAG", false)
                intent.putExtra("ID_USER", idUser)
                intent.putExtra("LEVEL_USER", levelUser)
                val auth = FirebaseAuth.getInstance()
                auth.signOut()
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
                mainEntryPresenter.setLevel(id, level)
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


    private fun alertDialogSetLanguage(language: Int) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Вы уверены, что хотите изменить язык?")
        alertDialog.setMessage("Учтите, что словарь будет отображать слова только для выбранного языка. Изученные слова в словаре и тестах будут доступны, только при смене языка обратно")
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "Да",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                val id: String = intent.getSerializableExtra("ID_USER") as String
                mainEntryPresenter.setLanguage(id, language)
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

    override fun onBackPressed() {
        moveTaskToBack(true)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun setAlarm(userDto: UserDto) {
        val intent = Intent(this, NotifyService::class.java)
        intent.putExtra("START_FLAG", true)
        intent.putExtra("ID_USER", userDto.userId)
        intent.putExtra("LEVEL_USER", userDto.level)
        startService(intent)
    }
}




