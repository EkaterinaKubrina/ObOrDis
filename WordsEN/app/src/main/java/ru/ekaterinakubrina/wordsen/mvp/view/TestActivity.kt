package ru.ekaterinakubrina.wordsen.mvp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.mvp.contracts.TestContract
import ru.ekaterinakubrina.wordsen.mvp.presenter.TestPresenter

class TestActivity : AppCompatActivity(), TestContract.View {
    private val testPresenter : TestContract.Presenter = TestPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val correct1: RadioButton = findViewById(R.id.correct1)
        val correct2: RadioButton = findViewById(R.id.correct2)
        val correct3: RadioButton = findViewById(R.id.correct3)
        val correct4: RadioButton = findViewById(R.id.correct4)
        val correct5: RadioButton = findViewById(R.id.correct5)
        val correct6: RadioButton = findViewById(R.id.correct6)
        val correct7: RadioButton = findViewById(R.id.correct7)
        val correct8: RadioButton = findViewById(R.id.correct8)
        val correct9: RadioButton = findViewById(R.id.correct9)
        val correct10: RadioButton = findViewById(R.id.correct10)

        var count = 0
        val buttonEnd: Button = findViewById(R.id.buttonTheEnd)
        buttonEnd.setOnClickListener {
            if (correct1.isChecked) {
                count++
            }
            if (correct2.isChecked) {
                count++
            }
            if (correct3.isChecked) {
                count += 2
            }
            if (correct4.isChecked) {
                count += 2
            }
            if (correct5.isChecked) {
                count += 3
            }
            if (correct6.isChecked) {
                count += 3
            }
            if (correct7.isChecked) {
                count += 4
            }
            if (correct8.isChecked) {
                count += 4
            }
            if (correct9.isChecked) {
                count += 5
            }
            if (correct10.isChecked) {
                count += 5
            }
            count /= 2
            testPresenter.clickOnEndButton(count, intent.getSerializableExtra("ID_USER") as String)
        }
    }

    override fun nextActivity(id: String, level: Int) {
        val intent = Intent(this, MainEntryActivity::class.java)
        intent.putExtra("ID_USER", id)
        intent.putExtra("LEVEL_USER", level)
        startActivity(intent)
    }
}