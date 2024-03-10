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



    private val binding: ActivityTweetsBinding by lazy {
        ActivityTweetsBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)


        val fabTweet = findViewById<FloatingActionButton>(R.id.fab_tweet)
        fabTweet.setOnClickListener {
            val intent = Intent(this, AddTweets::class.java)
            startActivity(intent)
        }


    }

    private fun fetchData() {
        val databaseReference = FirebaseDatabase.getInstance("https://opentweets-c7c0b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Tweets")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val linearLayout = findViewById<LinearLayout>(R.id.shoeLinear)
                linearLayout.removeAllViews() // Clear previous views
                for (snapshot in dataSnapshot.children) {
                    var text = snapshot.getValue(String::class.java)
                    val textView = TextView(this@Tweets).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        text = text
                        Toast.makeText(this@Tweets, text, Toast.LENGTH_SHORT).show()
                    }
                    linearLayout.addView(textView)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors
            }
        })
    }
}
