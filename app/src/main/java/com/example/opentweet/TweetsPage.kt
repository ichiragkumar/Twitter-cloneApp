package com.example.opentweet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class TweetsPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var tweetAdapter: ArrayAdapter<String>
    private var tweetsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets_page)
        database = FirebaseDatabase.getInstance("https://opentweets-c7c0b-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        tweetAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tweetsList)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = tweetAdapter

        fetchTweets()
    }
    private fun fetchTweets() {
        database.child("Tweets").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tweetsList.clear()
                for (tweetSnapshot in dataSnapshot.children) {
                    val tweet = tweetSnapshot.getValue(TweetDataClass::class.java)
                    tweet?.let {
//                        val displayText = "${it.title}: ${it.description}"
//                        tweetsList.add(displayText)
                        val sdf = SimpleDateFormat("MMM yyyy || HH:mm", Locale.getDefault())
                        val timestamp = it.timestamp ?: System.currentTimeMillis()
                        val dateString = sdf.format(Date(timestamp))
                        val displayText = "${"  " +it.title?.toUpperCase() + "\n\n"}  ${it.description + "\n" + "                                    "}  $dateString"
                        tweetsList.add(displayText)
                    }

                }
                tweetAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }



}