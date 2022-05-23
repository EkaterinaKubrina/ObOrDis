package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.presenter.SelectTestPresenter

class SelectTestActivity : AppCompatActivity(), SelectTestContractView {
    private val selectTestPresenter = SelectTestPresenter(this, this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_test)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        selectTestPresenter.availableTest(uid)

        val startWeekTest: Button = findViewById(R.id.startWeekTest)
        startWeekTest.setOnClickListener {
            val intent = Intent(this, TestingWordActivity::class.java)
            intent.putExtra("ID_USER", uid)
            intent.putExtra("TEST_TYPE", "week")
            startActivity(intent)
        }

        val startAllTest: Button = findViewById(R.id.startAllTest)
        startAllTest.setOnClickListener {
            val intent = Intent(this, TestingWordActivity::class.java)
            intent.putExtra("ID_USER", uid)
            intent.putExtra("TEST_TYPE", "all")
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setAvailableWeekTest() {
        val startWeekTest: Button = findViewById(R.id.startWeekTest)
        startWeekTest.isEnabled = true
        startWeekTest.setTextColor(getColor(R.color.myfon))
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun setNotAvailableWeekTest() {
        val startWeekTest: Button = findViewById(R.id.startWeekTest)
        startWeekTest.isEnabled = false
        startWeekTest.setTextColor(getColor(R.color.darkpurpl))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setAvailableAllTest() {
        val startAllTest: Button = findViewById(R.id.startAllTest)
        startAllTest.isEnabled = true
        startAllTest.setTextColor(getColor(R.color.myfon))
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun setNotAvailableAllTest() {
        val startAllTest: Button = findViewById(R.id.startAllTest)
        startAllTest.isEnabled = false
        startAllTest.setTextColor(getColor(R.color.darkpurpl))
    }

}