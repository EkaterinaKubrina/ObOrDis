package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter

class SplashActivity : AppCompatActivity() {
    private val userPresenter = UserPresenter(UserDaoImpl(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val user = auth.currentUser!!.uid.let { userPresenter.getUser(it) }
            if (user != null) {
                val intent = Intent(this, MainEntryActivity::class.java)
                intent.putExtra("ID_USER", user.userId)
                startActivity(intent)
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()


    }
}