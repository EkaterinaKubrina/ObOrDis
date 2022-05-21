package ru.ekaterinakubrina.wordsen.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.notify.NotifyService
import ru.ekaterinakubrina.wordsen.presenter.UserPresenter
import ru.ekaterinakubrina.wordsen.presenter.WordPresenter


class EntryActivity : AppCompatActivity(), EntryContractView {
    private val userPresenter = UserPresenter(this)
    private val wordPresenter = WordPresenter(WordDaoImpl(this))

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val email: EditText = findViewById(R.id.TextEmail)
        val password: EditText = findViewById(R.id.TextPassword)
        val buttonEntry: Button = findViewById(R.id.buttonEntry)
        val forgotPassButton: Button = findViewById(R.id.button6)
        val textError: TextView = findViewById(R.id.error)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)


        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            auth.signOut()
        }

        buttonEntry.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        signIn(auth, email.text.toString(), password.text.toString())
                    } else {
                        textError.text = task.exception?.message
                        progressBar.visibility = ProgressBar.INVISIBLE
                    }
                }

        }

        forgotPassButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun signIn(auth: FirebaseAuth, email: String, password: String) {
        var user = userPresenter.signIn(email, password)

        if (user == null) {
            val myRef = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (userPresenter.signIn(email, password) == null) {
                        userPresenter.addUserLocalDB(
                            auth.uid!!,
                            dataSnapshot.child("name").getValue(String::class.java),
                            email,
                            password
                        )

                        userPresenter.setLevelLocalDB(
                            auth.uid,
                            dataSnapshot.child("level").getValue(Int::class.java)
                        )

                        for (snapshot in dataSnapshot.child("new_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            wordPresenter.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        for (snapshot in dataSnapshot.child("studied_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            wordPresenter.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        user = userPresenter.signIn(email, password)
                        nextActivity(user!!)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } else {
            nextActivity(user!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun nextActivity(userDto: UserDto) {
        setAlarm(userDto)

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(userDto: UserDto) {
        val intent = Intent(this, NotifyService::class.java)
        intent.putExtra("START_FLAG", true)
        intent.putExtra("ID_USER", userDto.userId)
        intent.putExtra("LEVEL_USER", userDto.level)
        startService(intent)
    }


}