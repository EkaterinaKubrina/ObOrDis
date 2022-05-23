package ru.ekaterinakubrina.wordsen.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.presenter.SelectLevelPresenter

class SelectLevelActivity : AppCompatActivity(), SelectLevelContractView {
    private val selectLevelPresenter = SelectLevelPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_level)

        var level = 0

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup2)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                when (checkedId) {
                    R.id.radioButton1 -> level = MyDbWordsEN.Users.LEVEL_A0
                    R.id.radioButton2 -> level = MyDbWordsEN.Users.LEVEL_A1
                    R.id.radioButton3 -> level = MyDbWordsEN.Users.LEVEL_A2
                    R.id.radioButton4 -> level = MyDbWordsEN.Users.LEVEL_B1
                    R.id.radioButton5 -> level = MyDbWordsEN.Users.LEVEL_B2
                    R.id.radioButton6 -> level = MyDbWordsEN.Users.LEVEL_C1
                }
            }
        }

        val button: Button = findViewById(R.id.button5)
        button.setOnClickListener {
            alertDialogSetLevel(level)
        }
    }

    override fun nextActivity(id: String, level: Int) {
        val intent = Intent(this, MainEntryActivity::class.java)
        intent.putExtra("ID_USER", id)
        intent.putExtra("LEVEL_USER", level)
        startActivity(intent)
    }

    private fun alertDialogSetLevel(level: Int) {
        val id: String = intent.getSerializableExtra("ID_USER") as String

        val alertDialog: AlertDialog =
            AlertDialog.Builder(this).create()
        alertDialog.setTitle("Подтвердить выбор уровня?")
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "Да",
            DialogInterface.OnClickListener(fun(
                _: DialogInterface, _: Int
            ) {
                selectLevelPresenter.selectLevel(id, level)
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