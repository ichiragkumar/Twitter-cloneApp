package com.example.opentweet

import android.content.Intent
import com.example.opentweet.databinding.ActivityTweetsBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


private lateinit var database: DatabaseReference

class Tweets : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)


        val fabTweet = findViewById<FloatingActionButton>(R.id.fab_tweet)
        fabTweet.setOnClickListener {
            val intent = Intent(this, AddTweets::class.java)
            startActivity(intent)
        }
        checkUserLoggedIn()


    }
    private fun checkUserLoggedIn() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val email = user.email
            // Now you can display the email on your TweetScreen
            // This is a placeholder function call, replace it with your actual method to display the email
            displayEmailOnTweetScreen(email)
            displayUser(email)
        } else {
            // No user is signed in
            // Redirect to login screen or handle accordingly
            redirectToLoginScreen()
        }
    }
    private fun displayEmailOnTweetScreen(email: String?) {
        // This is a placeholder function. Implement how you wish to display the email.
        // For example, if you're using a TextView in your layout to show the email, you could do:
        if (email != null) {
            val emailTextView: TextView = findViewById(R.id.textView2) // Make sure you have a TextView with this ID in your layout
            emailTextView.text = email
        }
    }
    private fun displayUser(email: String?) {
        email?.let {
            if (it.isNotEmpty()) {
                val firstLetter = it.first().toString().toUpperCase() // Get the first letter and convert it to uppercase
                val firstLetterTextView: TextView = findViewById(R.id.textView3) // Assuming you have a TextView with this ID
                firstLetterTextView.text = firstLetter
            }
        }
    }


    private fun redirectToLoginScreen() {
        // Implement your logic to redirect the user to the login screen
        // This might involve starting a new Activity
    }
}




