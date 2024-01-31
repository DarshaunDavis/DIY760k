    package com.lislal.diy760

    import android.annotation.SuppressLint
    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.viewpager2.widget.ViewPager2
    import com.google.android.gms.ads.AdRequest
    import com.google.android.gms.ads.AdView
    import com.google.android.gms.ads.MobileAds
    import com.google.android.material.tabs.TabLayout
    import com.google.android.material.tabs.TabLayoutMediator
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.FirebaseDatabase
    import java.util.Calendar

    class MainActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private var backPressedTime: Long = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Initialize the Mobile Ads SDK
            MobileAds.initialize(this) { }

            val adView = findViewById<AdView>(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            // Initialize ViewPager2 with the adapter
            val viewPager: ViewPager2 = findViewById(R.id.viewPager)
            viewPager.adapter = ViewPagerAdapter(this)
            viewPager.setCurrentItem(1, false) // Set MainFragment as the initial fragment

            val tabLayout: TabLayout = findViewById(R.id.tabLayout)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Guided Repair"
                    1 -> "Home"
                    2 -> "Self-Repair"
                    else -> null
                }
                val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
                //val logOutButton = findViewById<Button>(R.id.logOutButton)

                // Check if the user is logged in
                if (currentUser != null) {
                    val userId = currentUser.uid
                    // Fetch the user's first name from Firebase Realtime Database
                    val database = FirebaseDatabase.getInstance().reference
                    database.child("users").child(userId).child("firstName").get()
                        .addOnSuccessListener { snapshot ->
                            val firstName = snapshot.value as? String
                        val greeting = getGreetingMessage() + firstName + "!"
                        greetingTextView.text = greeting
                        }
                        .addOnFailureListener {
                            greetingTextView.text = getGreetingMessage()
                        }
                }
            }

                /* logOutButton.setOnClickListener {
                // Log out from Firebase Authentication
                auth.signOut()
                // Navigate back to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } */

                .attach()

            viewPager.setCurrentItem(1, false) // Set MainFragment as the initial fragment
        }

        private fun getGreetingMessage(): String {
            return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> "Good morning, "
                in 12..16 -> "Good afternoon, "
                else -> "Good evening, "
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