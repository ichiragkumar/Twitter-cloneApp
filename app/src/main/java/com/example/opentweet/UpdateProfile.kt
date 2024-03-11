package com.example.opentweet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.opentweet.databinding.ActivityTweetsBinding
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
import java.util.Locale

class UpdateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val genderEditText = findViewById<EditText>(R.id.genderEditText)
        genderEditText.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            source.toString().toUpperCase(Locale.ROOT).let { input ->

                    if (input == "M" || input == "F" ) input else ""

            }
        })

        val ageEditText = findViewById<EditText>(R.id.ageEditText)
        ageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used here
            }
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty()) {
                    val age = text.toIntOrNull()
                    if (age != null) {
                        if (age < 1) {
                            ageEditText.setText("1")
                            ageEditText.setSelection(ageEditText.text.length) // Move cursor to end
                        } else if (age > 120) {
                            ageEditText.setText("120")
                            ageEditText.setSelection(ageEditText.text.length) // Move cursor to end
                        }
                    }
                }
            }
        })


        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        dobEditText.setOnClickListener {
            showDatePickerDialog()
        }
        val saveButton = findViewById<Button>(R.id.saveButton)


        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val gender = genderEditText.text.toString().trim()
            val age = ageEditText.text.toString().trim()
            val dob = dobEditText.text.toString().trim()


            // Simple validation
            if (name.isEmpty() || gender.isEmpty() || age.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Save data to Firebase
            val ref =
                FirebaseDatabase.getInstance("https://opentweets-c7c0b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users")
//            val userId = ref.push().key!!

            val user = User(name, gender, age.toInt(), dob)
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                ref.child(userId).setValue(user).addOnCompleteListener {
                    Toast.makeText(applicationContext, "Saved ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Tweets::class.java)
                    startActivity(intent)
                    // Optionally, if you want to finish the current activity
                    finish()
                }
            } else {
                Toast.makeText(applicationContext, "User not logged in", Toast.LENGTH_SHORT)
                    .show()
            }


//            ref.child(userId).setValue(user).addOnCompleteListener {
//                Toast.makeText(applicationContext, "Saved ", Toast.LENGTH_SHORT).show()
//            }

        }


        //    data class User(val name: String, val gender: String, val age: Int, val dob: String)
        // User.kt



    }

    data class User(
        val name: String = "",
        val gender: String = "",
        val age: Int = 0,
        val dob: String = ""
    )
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            findViewById<EditText>(R.id.dobEditText).setText(dateFormat.format(selectedDate.time))
        }, year, month, day)

        datePickerDialog.show()
    }


}