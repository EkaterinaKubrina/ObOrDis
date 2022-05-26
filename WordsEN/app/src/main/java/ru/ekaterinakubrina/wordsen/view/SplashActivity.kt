package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.contracts.SplashContract
import ru.ekaterinakubrina.wordsen.presenter.SplashPresenter

open class SplashActivity : AppCompatActivity(), SplashContract.View {
    private val splashPresenter : SplashContract.Presenter = SplashPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)
        splashPresenter.checkAuth()
        finish()
    }

    override fun authorized(uid: String) {
        val intent = Intent(this, MainEntryActivity::class.java)
        intent.putExtra("ID_USER", uid)
        startActivity(intent)
    }

    override fun notAuthorized() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}