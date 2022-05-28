package ru.ekaterinakubrina.wordsen.mvp.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R

class FirstInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_in)

        val id: String = intent.getSerializableExtra("ID_USER") as String

        val buttonSelect: Button = findViewById(R.id.select_level)
        buttonSelect.setOnClickListener {
            val intent = Intent(this, SelectLevelActivity::class.java)
            intent.putExtra("ID_USER", id)
            startActivity(intent)
        }

        val buttonTest: Button = findViewById(R.id.do_test)
        buttonTest.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("ID_USER", id)
            startActivity(intent)
        }

        val imageView6: ImageView = findViewById(R.id.imageView6)
        val objectAnimator = ObjectAnimator.ofFloat(imageView6, "rotation", 360F)
        objectAnimator.duration = 4000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ObjectAnimator.RESTART / REVERSE
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()
    }


    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}