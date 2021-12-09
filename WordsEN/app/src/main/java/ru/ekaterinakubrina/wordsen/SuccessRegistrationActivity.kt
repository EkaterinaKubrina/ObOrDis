package ru.ekaterinakubrina.wordsen

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SuccessRegistrationActivity() : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_registration)
        val flag : Boolean
        val buttonBack: Button = findViewById(R.id.button2)

        var str : String? = intent.getSerializableExtra("NAME_USER") as String?
        val text: TextView = findViewById(R.id.textSuccess)
        if(str != null){
            text.text = str.plus(", Ваша регистрация прошла успешно!")
            intent.removeExtra("NAME_USER")
            flag = true
            buttonBack.text = "Вернуться в меню"
        }
        else {
            flag = false
            buttonBack.text = "Вернуться назад"
            str = intent.getSerializableExtra("ERROR") as String?
            if(str != null){
                text.text = "Ошибка регистрации!".plus(str)
                intent.removeExtra("ERROR")
            }
            else {
                text.text = "Не удалось зарегистрироваться."
            }
        }

        buttonBack.setOnClickListener {
            if(flag) {
                val intent = intent.setClass(this@SuccessRegistrationActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = intent.setClass(this@SuccessRegistrationActivity, RegistrationActivity::class.java)
                startActivity(intent)
            }

        }


    }

}