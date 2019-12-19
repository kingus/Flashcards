package com.peargrammers.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_register)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        println("**************************************")
        println("**************************************")
        println("**************************************")
        println("**************************************")
        println(currentUser)
        println("**************************************")
        println("**************************************")
        println("**************************************")
        println("**************************************")
        //updateUI(currentUser)
    }
}
