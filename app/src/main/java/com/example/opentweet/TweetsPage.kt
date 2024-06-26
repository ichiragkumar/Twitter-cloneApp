package com.example.opentweet

import android.content.Intent
import android.net.ParseException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
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
    private var tweetObjects = ArrayList<TweetDataClass>()
    private var dataList: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets_page)


        val addTweetPAge = findViewById<ImageButton>(R.id.imageButton)
        addTweetPAge.setOnClickListener{
                // Start ProfileScreen activity
                val intent = Intent(this, AddTweets::class.java)
                startActivity(intent)

        }

        val profilePage = findViewById<ImageButton>(R.id.imageButton2)
        profilePage.setOnClickListener{
                // Start ProfileScreen activity
                val intent = Intent(this, Tweets::class.java)
                startActivity(intent)

        }


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

                       val sortButton: Button = findViewById(R.id.sortButton)
                        sortButton.setOnClickListener {
                            // Toggle the sort order
                            isSortedOldToNew = !isSortedOldToNew

                            // Sort tweetObjects based on the timestamp
                            val displayList = tweetObjects.map { tweet ->
                                // Format for displayText
                                "${"  " + tweet.title?.toUpperCase() + "\n\n"}  ${tweet.description + "\n"} ${tweet.timestamp}"
                            }


                            if (isSortedOldToNew) {
                                Toast.makeText(this@TweetsPage, "First sorted from new to old", Toast.LENGTH_SHORT).show()
                                tweetsList.reverse() // This reverses the order of items in dataList
                                tweetAdapter.notifyDataSetChanged()

                            } else {
                                tweetsList.reverse() // This reverses the order of items in dataList
                                tweetAdapter.notifyDataSetChanged()
                                Toast.makeText(this@TweetsPage, "Seccond sorted from new to old", Toast.LENGTH_SHORT).show()

                            }
                            sortButton.text =
                                if (isSortedOldToNew) "Sort New to Old" else "Sort Old to New"
                        }


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