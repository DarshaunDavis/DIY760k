package com.lislal.diy760

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val firstNameEditText = findViewById<TextInputEditText>(R.id.firstNameEditText)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

        val registerButton = findViewById<Button>(R.id.submitRegisterButton)
        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (validateForm(firstName, email, password, confirmPassword)) {
                createUser(email, password, firstName)
            }
        }

        val alreadyRegisteredTextView = findViewById<TextView>(R.id.alreadyRegisteredTextView)
        alreadyRegisteredTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateForm(firstName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (firstName.isEmpty()) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isEmailValid(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isPasswordStrong(password)) {
            Toast.makeText(this, "Password is not strong enough", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun createUser(email: String, password: String, firstName: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    saveUserToDatabase(userId, firstName)
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToDatabase(userId: String, firstName: String) {
        val database = FirebaseDatabase.getInstance().reference
        val userMap = mapOf("firstName" to firstName)

        database.child("users").child(userId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPasswordStrong(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

