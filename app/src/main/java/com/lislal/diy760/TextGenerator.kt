package com.lislal.diy760

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TextGenerator {

    // Function to validate input fields
    fun validateInputFields(customView: View, context: Context): Boolean {
        var isValid = true

        // Validate firstNameEditText
        val firstNameEditText = customView.findViewById<EditText>(R.id.firstNameEditText)
        if (firstNameEditText.text.toString().trim().isEmpty()) {
            firstNameEditText.error = "First name is required."
            isValid = false
        }

        // Validate lastNameEditText
        val lastNameEditText = customView.findViewById<EditText>(R.id.lastNameEditText)
        if (lastNameEditText.text.toString().trim().isEmpty()) {
            lastNameEditText.error = "Last name is required."
            isValid = false
        }

        // Validate addressNameEditText
        val addressEditText = customView.findViewById<EditText>(R.id.addressEditText)
        if (addressEditText.text.toString().trim().isEmpty()) {
            addressEditText.error = "Address is required."
            isValid = false
        }

        // Validate cityNameEditText
        val cityEditText = customView.findViewById<EditText>(R.id.cityEditText)
        if (cityEditText.text.toString().trim().isEmpty()) {
            cityEditText.error = "City is required."
            isValid = false
        }

        // Validate zipNameEditText
        val zipEditText = customView.findViewById<EditText>(R.id.zipEditText)
        if (zipEditText.text.toString().trim().isEmpty()) {
            zipEditText.error = "Zip Code is required."
            isValid = false
        }
        if (zipEditText.text.toString().trim().length != 5) {
            zipEditText.error = "Zip code must be exactly 5 characters."
            isValid = false
        }

        // Validate birthDatePickerButton specifically for the default text
        val birthDatePickerButton = customView.findViewById<Button>(R.id.birthDatePickerButton)
        val birthDateButtonText = birthDatePickerButton.text.toString().trim()

        // Check if the button text is the default "Select Birthdate" or empty
        if (birthDateButtonText == "Select Birthdate" || birthDateButtonText.isEmpty()) {
            // Setting error directly on a Button is not possible, so consider alternatives:
            // 1. Use a Toast message (as shown before)
            // 2. Use an associated TextView to show the error message
            // 3. Change the button's appearance to indicate an error (e.g., change text color)

            // Example using a Toast message:
            Toast.makeText(context, "Please select a birthdate.", Toast.LENGTH_LONG).show()
            isValid = false
        }

        // Validate socialNameEditText
        val socialEditText = customView.findViewById<EditText>(R.id.socialEditText)
        if (socialEditText.text.toString().trim().isEmpty()) {
            socialEditText.error = "Last 4 of social is required."
            isValid = false
        }
        if (socialEditText.text.toString().trim().length != 4) {
            socialEditText.error = "Social must be exactly 4 characters."
            isValid = false
        }

        // Add similar validations for other required fields...

        // Optionally, show a general message if any validation fails
        if (!isValid) {
            Toast.makeText(context, "Please fill out all required fields.", Toast.LENGTH_LONG)
                .show()
        }

        return isValid
    }

    fun setupStateSpinner(customView: View, context: Context) {
        val spinner = customView.findViewById<Spinner>(R.id.stateSpinner)
        if (spinner != null) {
            ArrayAdapter.createFromResource(
                context,
                R.array.us_states,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        } else {
            Log.d("TextGenerator", "Spinner not found in the current view.")
        }
    }

    // Function to add real-time validation listeners
    private fun addValidationListeners(customView: View) {
        val editTextIds = listOf(
            R.id.firstNameEditText,
            R.id.lastNameEditText,
            R.id.addressEditText,
            R.id.cityEditText,
            //R.id.stateEditText,
            R.id.zipEditText,
            R.id.socialEditText
            // Add other EditText IDs as needed
        )

        editTextIds.forEach { editTextId ->
            val editText = customView.findViewById<EditText>(editTextId)
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // Optionally re-validate the specific field or simply clear the error
                    editText.error = null
                }
            })
        }
    }

    // Function to generate text from layout
    fun generateTextFromLayout(
        customView: View,
        context: Context,
        radioSelections: Map<Int, String>
    ): String? {
        // First, validate the input fields
        if (!validateInputFields(customView, context)) {
            return null // Return null or handle validation failure as needed
        }

        // Retrieve the first name and last name from EditText fields
        val firstName = customView.findViewById<EditText>(R.id.firstNameEditText)?.text.toString().trim()
        val lastName = customView.findViewById<EditText>(R.id.lastNameEditText)?.text.toString().trim()

        // Proceed with text generation if validation passes
        val stringBuilder = StringBuilder()

        // Append the current date first
        val currentDate = getCurrentDate()
        stringBuilder.append("Date: $currentDate\n\n")

        // Fetch the company name and use it to retrieve the associated address
        val titleView = customView.findViewById<TextView>(R.id.headerTextView)?.text.toString().split(" ").getOrNull(0)?.trim()
        titleView?.let { companyName ->
            val formattedCompanyName = companyName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val addressForCompanyName = getAddressForTitle(context, companyName)
            if (addressForCompanyName.isNotEmpty()) {
                stringBuilder.append("$addressForCompanyName\n\n")
            }
            stringBuilder.append("Dear $formattedCompanyName,\n\n")
        }

        // Instantiate LetterGenerator and append its output to stringBuilder
        val letterGenerator = LetterGenerator(context, radioSelections)
        val letterContent = letterGenerator.generateRandomizedLetter(firstName, lastName)
        stringBuilder.append(letterContent)

        return stringBuilder.toString()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getAddressForTitle(context: Context, title: String): String {
        val addresses = context.resources.getStringArray(R.array.company_addresses)
        val keyWord = title.split(" ").firstOrNull() ?: return ""
        return addresses.firstOrNull { it.startsWith(keyWord, ignoreCase = true) } ?: ""
    }
}
