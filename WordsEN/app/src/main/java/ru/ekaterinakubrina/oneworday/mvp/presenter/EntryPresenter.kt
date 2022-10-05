package ru.ekaterinakubrina.oneworday.mvp.presenter

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.oneworday.mvp.contracts.EntryContract
import ru.ekaterinakubrina.oneworday.mvp.model.Repository

open class EntryPresenter(var context: Context, var entryContractView: EntryContract.View) :
    EntryContract.Presenter {
    private val repository: EntryContract.Model = Repository.getRepository(context)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun clickOnEntryButton() {
        val email = entryContractView.getEmail()
        val password = entryContractView.getPassword()
        entryContractView.setMyProgressBarVisibility(true)
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signIn(auth.uid!!)
                } else {
                    entryContractView.setMyProgressBarVisibility(false)
                    task.exception?.message?.let { entryContractView.setError(it) }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun signIn(uid: String) {
        var user = repository.getUser(uid)

        if (user == null) {
            val myRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (repository.getUser(uid) == null) {
                        repository.addUser(
                            uid,
                            dataSnapshot.child("name").getValue(String::class.java)!!
                        )
                        repository.setUserLanguage(
                                uid,
                                dataSnapshot.child("language").getValue(Int::class.java)!!
                        )
                        repository.setLevel(
                            uid,
                            dataSnapshot.child("level").getValue(Int::class.java)
                        )
                        for (snapshot in dataSnapshot.child("new_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            repository.addWordFromFB(uid, idWord, date!!, status!!)
                        }
                        for (snapshot in dataSnapshot.child("studied_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            repository.addWordFromFB(uid, idWord, date!!, status!!)
                        }
                        user = repository.getUser(uid)
                        entryContractView.nextActivity(user!!)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } else {
            entryContractView.nextActivity(user!!)
        }
    }


}