package com.example.opentweet
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class AddTweets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tweets)

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val postTweetButton = findViewById<Button>(R.id.postTweetButton)

        postTweetButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            // Basic validation
            if (title.isEmpty()) {
                titleEditText.error = "Please enter a title"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                descriptionEditText.error = "Please enter a description"
                return@setOnClickListener
            }

            // Post data to Firebase
            postDataToFirebase(title, description)
        }
    }

    private fun postDataToFirebase(title: String, description: String) {
        val databaseReference = FirebaseDatabase.getInstance("https://opentweets-c7c0b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Tweets")
        val tweetId = databaseReference.push().key // Generate a unique key for the post

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null && tweetId != null) {
            val tweet = TweetDataClass(title, description, currentUser.uid)
            databaseReference.child(tweetId).setValue(tweet).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Tweet posted successfully", Toast.LENGTH_SHORT).show()
                    finish() // Optionally finish this activity
                } else {
                    Toast.makeText(this, "Failed to post tweet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
data class Tweet(val title: String, val description: String, val userId: String)
