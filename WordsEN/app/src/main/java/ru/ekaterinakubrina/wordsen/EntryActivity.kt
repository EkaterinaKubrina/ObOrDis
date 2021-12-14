package ru.ekaterinakubrina.wordsen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import java.util.*

class EntryActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val email: EditText = findViewById(R.id.TextEmail)
        val password: EditText = findViewById(R.id.TextPassword)
        val buttonEntry: Button = findViewById(R.id.buttonEntry)
        val textView: TextView = findViewById(R.id.error)
        buttonEntry.setOnClickListener {
            myDbManager.openDb()
            val id = myDbManager.getUserId(email.text.toString(), password.text.toString())
            if(checkEmail(email.text.toString())) {
                if (checkPassword(password.text.toString())) {
                    if (id != null) {
                        val level = myDbManager.getLevelUser(id)
                        if(level != 0){
                            val intent = Intent(this@EntryActivity, MainEntryActivity::class.java)
                            intent.putExtra("ID_USER", id.toInt())
                            intent.putExtra("LEVEL_USER", level)
                            startActivity(intent)
                        }
                        else {
                            val intent = Intent(this@EntryActivity, FirstInActivity::class.java)
                            intent.putExtra("ID_USER", id.toInt())
                            startActivity(intent)
                        }
                    } else {
                        textView.text = "Ошибка: не удалось войти"
                    }
                }
                else {
                    textView.text = "Ошибка: некорректный пароль"
                }
            }
            else {
                textView.text = "Ошибка: некорректный адрес эл.почты"
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    private fun checkEmail(str : String) : Boolean {
        if(str.length < 3){
            return false
        }
        else if (str.contains("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})\$".toRegex())){
            return false
        }
        return true
    }

    private fun checkPassword(str : String) : Boolean {
        if(str.length < 6){
            return false
        }
        else if (str.contains("^[а-яА-я\\s]".toRegex())){
            return false
        }
        return true
    }


}