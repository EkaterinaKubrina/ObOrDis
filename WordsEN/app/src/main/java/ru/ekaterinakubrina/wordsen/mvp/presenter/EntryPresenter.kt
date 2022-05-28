package ru.ekaterinakubrina.wordsen.mvp.presenter

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.mvp.contracts.EntryContract
import ru.ekaterinakubrina.wordsen.mvp.model.Repository

open class EntryPresenter(var context: Context, var entryContractView: EntryContract.View) :
    EntryContract.Presenter {
    private val repository: Repository = Repository.getRepository(context)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun clickOnEntryButton() {
        val email = entryContractView.getEmail()
        val password = entryContractView.getPassword()
        entryContractView.setMyProgressBarVisibility(true)
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signIn(auth, email, password)
                } else {
                    entryContractView.setMyProgressBarVisibility(false)
                    task.exception?.message?.let { entryContractView.setError(it) }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun signIn(auth: FirebaseAuth, email: String, password: String) {
        var user = repository.getUserByPasswordAndEmail(email, password)

        if (user == null) {
            val myRef = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (repository.getUserByPasswordAndEmail(email, password) == null) {
                        repository.addUser(
                            auth.uid!!,
                            dataSnapshot.child("name").getValue(String::class.java)!!,
                            email,
                            password
                        )

                        repository.setLevel(
                            auth.uid,
                            dataSnapshot.child("level").getValue(Int::class.java)
                        )

                        for (snapshot in dataSnapshot.child("new_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            repository.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        for (snapshot in dataSnapshot.child("studied_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            repository.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        user = repository.getUserByPasswordAndEmail(email, password)
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