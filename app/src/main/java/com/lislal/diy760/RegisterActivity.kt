package com.lislal.diy760

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameEditText = findViewById<TextInputEditText>(R.id.firstNameEditText)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

        // Initialize the "Register" button and set its click listener
        val registerButton = findViewById<Button>(R.id.submitRegisterButton)
        registerButton.setOnClickListener {
            // Here, you can add validation logic for registration fields
            // For now, let's just navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize the "Already Registered" text view and set its click listener
        val alreadyRegisteredTextView = findViewById<TextView>(R.id.alreadyRegisteredTextView)
        alreadyRegisteredTextView.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
