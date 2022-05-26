package ru.ekaterinakubrina.wordsen.presenter

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ekaterinakubrina.wordsen.contracts.EntryContract
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.model.DictionaryModel
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel

open class EntryPresenter(var context: Context, var entryContractView: EntryContract.View) :
    EntryContract.Presenter {
    private val userModel = UsersModel(UserDaoImpl(context))
    private val wordModel = WordsModel(WordDaoImpl(context))
    private var dictionaryModel = DictionaryModel(DictionaryDaoImpl(context), wordModel)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun trySignIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signIn(auth, email, password)
                } else {
                    task.exception?.message?.let { entryContractView.setError(it) }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun signIn(auth: FirebaseAuth, email: String, password: String) {
        var user = userModel.getUserByPasswordAndEmail(email, password)

        if (user == null) {
            val myRef = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (userModel.getUserByPasswordAndEmail(email, password) == null) {
                        userModel.addUser(
                            auth.uid!!,
                            dataSnapshot.child("name").getValue(String::class.java)!!,
                            email,
                            password
                        )

                        userModel.setLevel(
                            auth.uid,
                            dataSnapshot.child("level").getValue(Int::class.java)
                        )

                        for (snapshot in dataSnapshot.child("new_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            dictionaryModel.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        for (snapshot in dataSnapshot.child("studied_words").children) {
                            val idWord = Integer.parseInt(snapshot.key as String)
                            val date = snapshot.child("date").getValue(Int::class.java)
                            val status = snapshot.child("status").getValue(Int::class.java)
                            dictionaryModel.addWordFromFB(auth.uid!!, idWord, date!!, status!!)
                        }

                        user = userModel.getUserByPasswordAndEmail(email, password)
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