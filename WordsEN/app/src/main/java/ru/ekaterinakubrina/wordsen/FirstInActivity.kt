package ru.ekaterinakubrina.wordsen

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class FirstInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_in)

        val imageView6 : ImageView = findViewById(R.id.imageView6)
        val buttonSelect : Button = findViewById(R.id.select_level)
        val buttonTest : Button = findViewById(R.id.do_test)

        val id : Int = intent.getSerializableExtra("ID_USER") as Int

        val objectAnimator = ObjectAnimator.ofFloat(imageView6, "rotation", 360F)
        objectAnimator.duration = 4000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ObjectAnimator.RESTART / REVERSE
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()

        buttonSelect.setOnClickListener {
            val intent = Intent(this@FirstInActivity, SelectLevelActivity::class.java)
            intent.putExtra("ID_USER", id)
            startActivity(intent)
        }

        buttonTest.setOnClickListener {
            val intent = Intent(this@FirstInActivity, TestActivity::class.java)
            intent.putExtra("ID_USER", id)
            startActivity(intent)
        }

    }
}