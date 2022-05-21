package ru.ekaterinakubrina.wordsen.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.data.MyDbWordsEN
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter


class SelectLevelActivity : AppCompatActivity() {
    private val userPresenter = UserPresenter(UserDaoImpl(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_level)

        val checkBox: CheckBox = findViewById(R.id.checkBox)
        val checkBox2: CheckBox = findViewById(R.id.checkBox2)
        val checkBox3: CheckBox = findViewById(R.id.checkBox3)
        val checkBox4: CheckBox = findViewById(R.id.checkBox4)
        val checkBox5: CheckBox = findViewById(R.id.checkBox5)
        val checkBox6: CheckBox = findViewById(R.id.checkBox6)
        val button: Button = findViewById(R.id.button5)

        var level = 0

        checkBox.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_A0

        }
        checkBox2.setOnClickListener {
            checkBox.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_A1
        }
        checkBox3.setOnClickListener {
            checkBox2.isChecked = false
            checkBox.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_A2
        }
        checkBox4.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_B1
        }
        checkBox5.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox.isChecked = false
            checkBox6.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_B2
        }
        checkBox6.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox.isChecked = false
            level = MyDbWordsEN.Users.LEVEL_C1
        }

        button.setOnClickListener {
            val id: String = intent.getSerializableExtra("ID_USER") as String
            val alertDialog: AlertDialog =
                AlertDialog.Builder(this@SelectLevelActivity).create()
            alertDialog.setTitle("Подтвердить выбор уровня?")
            alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "Да",
                DialogInterface.OnClickListener(fun(
                    _: DialogInterface, _: Int
                ) {
                    userPresenter.setLevel(id, level)
                    val intent = Intent(this@SelectLevelActivity, MainEntryActivity::class.java)
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
}