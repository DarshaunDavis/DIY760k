package com.lislal.diy760

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TextGenerator {
    fun generateTextFromLayout(customView: View, context: Context, radioSelections: Map<Int, String>): String {
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

        // New code to add the address based on title
        val titleView = customView.findViewById<TextView>(R.id.headerTextView)
        titleView?.let {
            val title = it.text.toString()
            val addresses = getAddressForTitle(context, title)
            if (addresses.isNotEmpty()) {
                stringBuilder.append("\n$addresses\n\n")
            }
        }

        // Assuming the layout name is similar to the title and you can extract the first word
        val companyName = titleView?.text.toString().split(" ")[0] // Extract the first word
        val formattedCompanyName = companyName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        stringBuilder.append("Dear $formattedCompanyName,\n\n")

        // Instantiate LetterGenerator and append its output to stringBuilder
        val letterGenerator = LetterGenerator(context, radioSelections) // Pass radioSelections here
        val randomizedLetterContent = letterGenerator.generateRandomizedLetter()
        stringBuilder.append(randomizedLetterContent)

        // Example of appending radio button selections to the generated text
        radioSelections.forEach { (radioGroupId, selection) ->
            val radioGroupResourceName = context.resources.getResourceEntryName(radioGroupId)
            stringBuilder.append("$radioGroupResourceName: $selection\n\n")
        }

        // Add more checks for other input types if needed
        // For example, you could check for RadioButtons, Spinners, etc.

        return stringBuilder.toString()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getAddressForTitle(context: Context, title: String): String {
        val addresses = context.resources.getStringArray(R.array.company_addresses)
        // Assuming the title format is "Experian Step 1 Letter", split by space and get the first word
        val keyWord = title.split(" ").firstOrNull() ?: return ""
        // Find the address that starts with the key word
        return addresses.firstOrNull { it.startsWith(keyWord, ignoreCase = true) } ?: ""
    }


}