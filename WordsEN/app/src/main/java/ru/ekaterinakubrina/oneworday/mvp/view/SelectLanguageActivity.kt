package ru.ekaterinakubrina.oneworday.mvp.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.oneworday.R
import ru.ekaterinakubrina.oneworday.mvp.contracts.SelectLanguageContract
import ru.ekaterinakubrina.oneworday.mvp.presenter.SelectLanguagePresenter


class SelectLanguageActivity : AppCompatActivity(), SelectLanguageContract.View {
    private val selectLanguagePresenter: SelectLanguageContract.Presenter =
        SelectLanguagePresenter(this, this)
    val data = arrayOf("Английский", "Французский")
    var positionSelect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)

        selectLanguagePresenter.initLanguages()

        val imageView6: ImageView = findViewById(R.id.imageView10)
        val objectAnimator = ObjectAnimator.ofFloat(imageView6, "rotation", 360F)
        objectAnimator.duration = 4000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ObjectAnimator.RESTART / ValueAnimator.REVERSE
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()

        val buttonNext: Button = findViewById(R.id.buttonNext)
        buttonNext.setOnClickListener {
            val id: String = intent.getSerializableExtra("ID_USER") as String
            selectLanguagePresenter.clickOnOkButton(id, positionSelect)
        }
    }

    override fun nextActivity() {
        val id: String = intent.getSerializableExtra("ID_USER") as String
        val intent = Intent(this, FirstInActivity::class.java)
        intent.putExtra("ID_USER", id)
        intent.putExtra("LANGUAGE", positionSelect)
        startActivity(intent)
    }

    override fun initSelectSpinner(data: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    positionSelect = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinner.onItemSelectedListener = itemSelectedListener
    }
}