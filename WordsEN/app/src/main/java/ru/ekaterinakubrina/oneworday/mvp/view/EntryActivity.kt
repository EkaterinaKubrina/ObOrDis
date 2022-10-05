package ru.ekaterinakubrina.oneworday.mvp.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.ekaterinakubrina.oneworday.R
import ru.ekaterinakubrina.oneworday.dto.UserDto
import ru.ekaterinakubrina.oneworday.mvp.contracts.EntryContract
import ru.ekaterinakubrina.oneworday.mvp.presenter.EntryPresenter


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
        if (userDto.level != 0) {
            val intent = Intent(this, MainEntryActivity::class.java)
            intent.putExtra("ID_USER", userDto.userId)
            startActivity(intent)
        } else {
            val intent = Intent(this, SelectLanguageActivity::class.java)
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

}