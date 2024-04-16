package com.example.opentweet

import android.content.Intent
import com.example.opentweet.databinding.ActivityTweetsBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
        fetchLoggedInUserData()


        val fabTweet = findViewById<FloatingActionButton>(R.id.addProfile)
        fabTweet.setOnClickListener {
            val intent = Intent(this, UpdateProfile::class.java)
            startActivity(intent)
        }

        val exploreButton = findViewById<Button>(R.id.button4)
        exploreButton.setOnClickListener {
            val intent = Intent(this, TweetsPage::class.java)
            startActivity(intent)
        }

        val fabTweetToProfile = findViewById<FloatingActionButton>(R.id.fab_tweet)
        fabTweetToProfile.setOnClickListener {
            val intent2 = Intent(this, AddTweets::class.java)
            startActivity(intent2)
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

    private fun fetchLoggedInUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        println(userId)
        if (userId != null) {
            val ref = FirebaseDatabase.getInstance("https://opentweets-c7c0b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(userId)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UpdateProfile.User::class.java)
                    user?.let {
                        // Update UI with the logged-in user's data
                        findViewById<TextView>(R.id.userNameTextView).text ="" + user.name.uppercase()
                        findViewById<TextView>(R.id.userGenderTextView).text ="" + user.gender
                        findViewById<TextView>(R.id.userAgeTextView).text ="" + user.age.toString()
                        findViewById<TextView>(R.id.userDobTextView).text ="" + user.dob
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Database error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(applicationContext, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

}

data class User(
    val name: String = "",
    val gender: String = "",
    val age: Int = 0,
    val dob: String = ""
)





