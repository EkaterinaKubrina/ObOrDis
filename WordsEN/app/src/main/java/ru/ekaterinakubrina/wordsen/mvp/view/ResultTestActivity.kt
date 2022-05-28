package ru.ekaterinakubrina.wordsen.mvp.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.mvp.contracts.ResultTestContract
import ru.ekaterinakubrina.wordsen.mvp.presenter.ResultTestPresenter

class ResultTestActivity : AppCompatActivity(), ResultTestContract.View {
    private val resultTestPresenter : ResultTestContract.Presenter = ResultTestPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_test)

        val resCount: Int = intent.getSerializableExtra("RESULT") as Int
        val questionCount: Int = intent.getSerializableExtra("QUESTIONS") as Int
        resultTestPresenter.getResult(resCount, questionCount)

        val uid: String = intent.getSerializableExtra("ID_USER") as String
        val buttonOk: Button = findViewById(R.id.buttonOk)
        buttonOk.setOnClickListener {
            val intent = Intent(this, MainEntryActivity::class.java)
            intent.putExtra("ID_USER", uid)
            startActivity(intent)
        }

        val imageView: ImageView = findViewById(R.id.imageView16)
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 360F)
        objectAnimator.duration = 4000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ObjectAnimator.RESTART / ValueAnimator.REVERSE
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()
    }

    override fun showResult(str: String){
        val resultText: TextView = findViewById(R.id.resultText)
        resultText.text = str
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}