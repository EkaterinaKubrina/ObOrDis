package ru.ekaterinakubrina.wordsen

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.data.MyDbManager


class SelectLevelActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_level)

        val checkBox : CheckBox = findViewById(R.id.checkBox)
        val checkBox2 : CheckBox = findViewById(R.id.checkBox2)
        val checkBox3 : CheckBox = findViewById(R.id.checkBox3)
        val checkBox4 : CheckBox = findViewById(R.id.checkBox4)
        val checkBox5 : CheckBox = findViewById(R.id.checkBox5)
        val checkBox6 : CheckBox = findViewById(R.id.checkBox6)
        val button : Button = findViewById(R.id.button5)
        var level = 0

        checkBox.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = 1

        }
        checkBox2.setOnClickListener {
            checkBox.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = 2
        }
        checkBox3.setOnClickListener {
            checkBox2.isChecked = false
            checkBox.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = 3
        }
        checkBox4.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox.isChecked = false
            checkBox5.isChecked = false
            checkBox6.isChecked = false
            level = 4
        }
        checkBox5.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox.isChecked = false
            checkBox6.isChecked = false
            level = 5
        }
        checkBox6.setOnClickListener {
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false
            checkBox.isChecked = false
            level = 6
        }

        button.setOnClickListener {
            val id : Int = intent.getSerializableExtra("ID_USER") as Int
            val alertDialog : android.app.AlertDialog? = android.app.AlertDialog.Builder(this@SelectLevelActivity).create()
            alertDialog?.setTitle("Подтвердить выбор уровня?")
            alertDialog?.setButton(DialogInterface.BUTTON_POSITIVE, "Да", DialogInterface.OnClickListener(fun (
                _: DialogInterface, _: Int){
                myDbManager.openDb()
                myDbManager.setLevelUser(id, level)
                myDbManager.closeDb()
                val intent = Intent(this@SelectLevelActivity, MainEntryActivity::class.java)
                intent.putExtra("ID_USER", id)
                intent.putExtra("LEVEL_USER", level)
                startActivity(intent)
            }))
            alertDialog?.setButton(DialogInterface.BUTTON_NEGATIVE, "Отменить", DialogInterface.OnClickListener(fun (
                _: DialogInterface, _: Int){
                alertDialog.cancel()
            }))
            alertDialog?.show()
        }

    }
}