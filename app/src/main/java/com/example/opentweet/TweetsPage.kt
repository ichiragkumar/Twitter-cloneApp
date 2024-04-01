package com.example.opentweet

import android.net.ParseException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import java.util.*



class TweetsPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var tweetAdapter: ArrayAdapter<String>
    private var tweetsList = ArrayList<String>()
    private var isSortedOldToNew = true


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

//                        val sortButton: Button = findViewById(R.id.sortButton)
//                        sortButton.setOnClickListener {
//                            // Toggle the sort order
//
//                            isSortedOldToNew = !isSortedOldToNew
//                            tweetsList.sortWith(compareBy {it.timestamp})
//                            if (!isSortedOldToNew) {
//                                tweetsList.reverse()
//                            }
//                            tweetAdapter.notifyDataSetChanged()
//                            sortButton.text = if (isSortedOldToNew) "Sort New to Old" else "Sort Old to New"
//                        }
//
                        val displayText = "${"  " + it.title?.toUpperCase() + "\n\n"}  ${it.description + "\n"} ${it.timestamp}"
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