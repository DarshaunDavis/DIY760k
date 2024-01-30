    package com.lislal.diy760

    import android.annotation.SuppressLint
    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.FirebaseDatabase

    class MainActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private var backPressedTime: Long = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
            val logOutButton = findViewById<Button>(R.id.logOutButton)

            // Check if the user is logged in
            if (currentUser != null) {
                val userId = currentUser.uid
                // Fetch the user's first name from Firebase Realtime Database
                val database = FirebaseDatabase.getInstance().reference
                database.child("users").child(userId).child("firstName").get()
                    .addOnSuccessListener { snapshot ->
                        val firstName = snapshot.value as? String
                        greetingTextView.text = "Hello $firstName!"
                    }
                    .addOnFailureListener {
                        greetingTextView.text = "Hello"
                    }
            }

            logOutButton.setOnClickListener {
                // Log out from Firebase Authentication
                auth.signOut()
                // Navigate back to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        @SuppressLint("MissingSuperCall")
        override fun onBackPressed() {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Press back again to log out and exit", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }