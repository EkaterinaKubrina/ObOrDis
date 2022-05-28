package ru.ekaterinakubrina.wordsen.mvp.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.mvp.contracts.EntryContract
import ru.ekaterinakubrina.wordsen.mvp.presenter.EntryPresenter
import ru.ekaterinakubrina.wordsen.notify.NotifyService


class EntryActivity : AppCompatActivity(), EntryContract.View {
    private var entryPresenter: EntryContract.Presenter = EntryPresenter(this, this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val buttonEntry: Button = findViewById(R.id.buttonEntry)
        buttonEntry.setOnClickListener {
            entryPresenter.clickOnEntryButton()
        }

        val forgotPassButton: Button = findViewById(R.id.buttonForgotPass)
        forgotPassButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setMyProgressBarVisibility(visible: Boolean) {
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        if (visible) {
            progressBar.visibility = ProgressBar.VISIBLE
        } else {
            progressBar.visibility = ProgressBar.INVISIBLE
        }
    }

    override fun getEmail(): String {
        val email: EditText = findViewById(R.id.TextEmail)
        return email.text.toString()
    }

    override fun getPassword(): String {
        val password: EditText = findViewById(R.id.TextPassword)
        return password.text.toString()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun nextActivity(userDto: UserDto) {
        setAlarm(userDto) //установка будильника для уведомлений
        if (userDto.level != 0) {
            val intent = Intent(this, MainEntryActivity::class.java)
            intent.putExtra("ID_USER", userDto.userId)
            startActivity(intent)
        } else {
            val intent = Intent(this, FirstInActivity::class.java)
            intent.putExtra("ID_USER", userDto.userId)
            startActivity(intent)
        }
    }

    override fun setError(message: String) {
        val textError: TextView = findViewById(R.id.error)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        textError.text = message
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(userDto: UserDto) {
        val intent = Intent(this, NotifyService::class.java)
        intent.putExtra("START_FLAG", true)
        intent.putExtra("ID_USER", userDto.userId)
        intent.putExtra("LEVEL_USER", userDto.level)
        startService(intent)
    }

}