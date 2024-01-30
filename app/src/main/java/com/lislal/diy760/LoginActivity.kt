package com.lislal.diy760

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize the "Login" button and set its click listener
        val loginButton = findViewById<Button>(R.id.submitLoginButton)
        loginButton.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize the "Register" text view and set its click listener
        val registerTextView = findViewById<TextView>(R.id.registerTextView)
        registerTextView.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
