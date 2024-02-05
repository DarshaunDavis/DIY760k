package com.lislal.diy760

import android.view.View
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        // Assuming the birthDatePickerButton's text is set to the selected birthdate
        customView.findViewById<Button>(R.id.birthDatePickerButton)?.let {
            val birthDate = it.text.toString()
            // Only append if birthDate is not the initial text like "Select birthdate"
            if (birthDate.isNotEmpty() && birthDate != "Select birthdate") { // Replace "Select birthdate" with the initial text of your button if different
                stringBuilder.append("Birthdate: $birthDate\n")
            }
        }

        customView.findViewById<EditText>(R.id.socialEditText)?.let {
            val social = it.text.toString()
            stringBuilder.append("SS#: ***-**-$social\n\n\n")
        }

        val currentDate = getCurrentDate()
        stringBuilder.append("Date: $currentDate\n")

        // Add more checks for other input types if needed
        // For example, you could check for RadioButtons, Spinners, etc.

        return stringBuilder.toString()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}