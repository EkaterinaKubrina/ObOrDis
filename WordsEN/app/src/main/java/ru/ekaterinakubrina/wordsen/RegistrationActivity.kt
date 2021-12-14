package ru.ekaterinakubrina.wordsen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.data.MyDbManager
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            myDbManager.openDb()
            val name: EditText = findViewById(R.id.textPersonName)
            val email: EditText = findViewById(R.id.textEmailAddress)
            val password: EditText = findViewById(R.id.textPassword)
            val password2: EditText = findViewById(R.id.textPassword2)
            val intent = Intent(this@RegistrationActivity, SuccessRegistrationActivity()::class.java)

            if(password.text.toString() == password2.text.toString()) {
                if (checkName(name.text.toString().trim())) {
                    if (checkEmail(email.text.toString().trim())) {
                        if (checkPassword(password.text.toString())) {
                            val id: Long? = myDbManager.insertUser(name.text.toString().trim().capitalize(Locale.ROOT), email.text.toString(), password.text.toString())
                            if (id?.compareTo(-1) != 0) {
                                intent.putExtra("NAME_USER", name.text.toString().trim().capitalize(Locale.ROOT))
                                startActivity(intent)
                            } else {
                                startActivity(intent)
                            }
                        } else {
                            intent.putExtra("ERROR", "Некорректный пароль")
                            startActivity(intent)
                        }
                    } else {
                        intent.putExtra("ERROR", "Некорректный email")
                        startActivity(intent)
                    }
                } else {
                    intent.putExtra("ERROR", "Некорректное имя пользователя")
                    startActivity(intent)
                }
            }
            else {
                intent.putExtra("ERROR", "Введены разные пароли")
                startActivity(intent)
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    private fun checkName(str : String) : Boolean {
        if(str.length < 2){
            return false
        }
        else if (str.contains("[^a-zA-ZА-Яа-я]".toRegex())){
            return false
        }
        return true
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