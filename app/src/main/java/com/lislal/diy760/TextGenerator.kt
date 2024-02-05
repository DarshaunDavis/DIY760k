package com.lislal.diy760

import android.view.View
import android.widget.EditText

class TextGenerator {
    fun generateTextFromLayout(customView: View): String {
        // Initialize an empty StringBuilder
        val stringBuilder = StringBuilder()

        // Example of handling different data entry methods
        customView.findViewById<EditText>(R.id.firstNameEditText)?.let {
            val firstName = it.text.toString()
            stringBuilder.append("$firstName ")
        }

        customView.findViewById<EditText>(R.id.lastNameEditText)?.let {
            val lastName = it.text.toString()
            stringBuilder.append("$lastName\n")
        }

        customView.findViewById<EditText>(R.id.addressEditText)?.let {
            val address = it.text.toString()
            stringBuilder.append("$address ")
        }

        customView.findViewById<EditText>(R.id.apartmentEditText)?.let {
            val apartment = it.text.toString()
            stringBuilder.append("$apartment\n")
        }

        customView.findViewById<EditText>(R.id.cityEditText)?.let {
            val city = it.text.toString()
            stringBuilder.append("$city, ")
        }

        customView.findViewById<EditText>(R.id.stateEditText)?.let {
            val state = it.text.toString()
            stringBuilder.append("$state ")
        }

        customView.findViewById<EditText>(R.id.zipEditText)?.let {
            val zipCode = it.text.toString()
            stringBuilder.append("$zipCode\n")
        }

        customView.findViewById<EditText>(R.id.socialEditText)?.let {
            val social = it.text.toString()
            stringBuilder.append("***-**-$social\n")
        }

        // Add more checks for other input types if needed
        // For example, you could check for RadioButtons, Spinners, etc.

        return stringBuilder.toString()
    }
}
