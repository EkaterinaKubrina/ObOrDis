package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.dto.UserDto
import ru.ekaterinakubrina.wordsen.model.User
import ru.ekaterinakubrina.wordsen.notify.NotifyService
import ru.ekaterinakubrina.wordsen.view.EntryContractView
import ru.ekaterinakubrina.wordsen.view.FirstInActivity
import ru.ekaterinakubrina.wordsen.view.MainEntryActivity

class EntryPresenter(context: Context, entryContractView: EntryContractView) {
    private val userModel = User(context)

    @RequiresApi(Build.VERSION_CODES.M)
    fun sign(auth: FirebaseAuth, email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signIn(auth, email, password)
                } else {
                    textError.text = task.exception?.message
                    progressBar.visibility = ProgressBar.INVISIBLE
                }
            }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun signIn(auth: FirebaseAuth, email: String, password: String) {
        var user = userModel.signIn(email, password)

        if (user == null) {
            val myRef = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (userModel.signIn(email, password) == null) {
                        userModel.addUserLocalDB(
                            auth.uid!!,
                            dataSnapshot.child("name").getValue(String::class.java),
                            email,
                            password
                        )

                        userModel.setLevelLocalDB(
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