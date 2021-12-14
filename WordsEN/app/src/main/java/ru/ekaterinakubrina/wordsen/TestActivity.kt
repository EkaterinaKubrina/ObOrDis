package ru.ekaterinakubrina.wordsen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.data.MyDbManager

class TestActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


    val correct1 : RadioButton = findViewById(R.id.correct1)
    val correct2 : RadioButton = findViewById(R.id.correct2)
    val correct3 : RadioButton = findViewById(R.id.correct3)
    val correct4 : RadioButton = findViewById(R.id.correct4)
    val correct5 : RadioButton = findViewById(R.id.correct5)
    val correct6 : RadioButton = findViewById(R.id.correct6)
    val correct7 : RadioButton = findViewById(R.id.correct7)
    val correct8 : RadioButton = findViewById(R.id.correct8)
    val correct9 : RadioButton = findViewById(R.id.correct9)
    val correct10 : RadioButton = findViewById(R.id.correct10)

    val buttonEnd : Button = findViewById(R.id.buttonTheEnd)
    var count = 0

    buttonEnd.setOnClickListener{
        if(correct1.isChecked){
            count++
        }
        if(correct2.isChecked){
            count++
        }
        if(correct3.isChecked){
            count=+2
        }
        if(correct4.isChecked){
            count=+2
        }
        if(correct5.isChecked){
            count=+3
        }
        if(correct6.isChecked){
            count=+3
        }
        if(correct7.isChecked){
            count=+4
        }
        if(correct8.isChecked){
            count=+4
        }
        if(correct9.isChecked){
            count=+5
        }
        if(correct10.isChecked){
            count=+5
        }

        count /= 2
        myDbManager.openDb()
        val id : Int = intent.getSerializableExtra("ID_USER") as Int
        val level : Int = when{
            count < 1 -> 1
            count < 2 -> 2
            count < 3 -> 3
            count < 6 -> 4
            count < 10 -> 5
            else -> 6
        }
        myDbManager.setLevelUser(id, level)

        val intent = Intent(this@TestActivity, MainEntryActivity::class.java)
        intent.putExtra("ID_USER", id)
        intent.putExtra("LEVEL_USER", level)
        startActivity(intent)
    }

    }
}