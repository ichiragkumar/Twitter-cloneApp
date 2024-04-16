package com.example.opentweet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddressl)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPasswordl)
        val loginButton = findViewById<Button>(R.id.buttonl)

        loginButton.setOnClickListener {
            Toast(this)
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                emailEditText.error = "Please enter your email"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Please enter your password"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordEditText.error = "Please enter your password"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Login successful, navigate to TweetScreen
                val intent = Intent(this, TweetsPage::class.java)
                startActivity(intent)
                finish()
            } else {
                // If login fails, check the exception to show the appropriate message
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The password is wrong
                    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
                } else if (task.exception is FirebaseAuthInvalidUserException) {
                    // The email does not exist
                    Toast.makeText(this, "Please create an account", Toast.LENGTH_SHORT).show()
                } else {
                    // Other errors
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}




