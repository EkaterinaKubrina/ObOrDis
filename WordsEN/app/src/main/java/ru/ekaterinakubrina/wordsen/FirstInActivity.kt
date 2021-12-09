package ru.ekaterinakubrina.wordsen

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FirstInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_in)

        val imageView6 : ImageView = findViewById(R.id.imageView6)
        val objectAnimator = ObjectAnimator.ofFloat(imageView6, "rotation", 360F)
        objectAnimator.duration = 4000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ObjectAnimator.RESTART / REVERSE
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()

    }
}