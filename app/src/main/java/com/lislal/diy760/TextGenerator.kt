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

    fun setupNameDisputeSpinner(customView: View, context: Context) {
        val spinner = customView.findViewById<Spinner>(R.id.name_spinner)
        ArrayAdapter.createFromResource(
            context,
            R.array.name_dispute_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun setupDisputeResultsSpinner(customView: View, context: Context) {
        val spinner = customView.findViewById<Spinner>(R.id.name_result_spinner)
        ArrayAdapter.createFromResource(
            context,
            R.array.dispute_results_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
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
        // Validate input fields first
        if (!validateInputFields(customView, context)) {
            return null // Return null if validation fails
        }

        val stringBuilder = StringBuilder()

        // Append the current date
        val currentDate = getCurrentDate()
        stringBuilder.append("Date: $currentDate\n\n")

        // Append user details
        val firstName = customView.findViewById<EditText>(R.id.firstNameEditText)?.text.toString().trim()
        val lastName = customView.findViewById<EditText>(R.id.lastNameEditText)?.text.toString().trim()
        val address = customView.findViewById<EditText>(R.id.addressEditText)?.text.toString().trim()
        val apartment = customView.findViewById<EditText>(R.id.apartmentEditText)?.text.toString().trim()
        val city = customView.findViewById<EditText>(R.id.cityEditText)?.text.toString().trim()
        val state = customView.findViewById<Spinner>(R.id.stateSpinner)?.selectedItem.toString()
        val zipCode = customView.findViewById<EditText>(R.id.zipEditText)?.text.toString().trim()
        val social = customView.findViewById<EditText>(R.id.socialEditText)?.text.toString().trim()
        val birthDate = customView.findViewById<Button>(R.id.birthDatePickerButton)?.text.toString().trim()
        val inaccurateName = customView.findViewById<EditText>(R.id.inaccurate_name)?.text.toString().trim()
        val disputeReason = customView.findViewById<Spinner>(R.id.name_spinner)?.selectedItem.toString()
        val disputeResult = customView.findViewById<Spinner>(R.id.name_result_spinner)?.selectedItem.toString()

        // Correctly format and append user details to stringBuilder
        stringBuilder.apply {
            append("$firstName $lastName\n")
            append("$address, $apartment\n")
            append("$city, $state $zipCode\n")
            append("Social: ***-**-$social\n")
            if (birthDate != "Select Birthdate") {
                append("Birthdate: $birthDate\n")
            }
            append("\n") // Extra newline for separation before letter content
        }

        // Fetch the company name and retrieve the associated address
        val titleViewText = customView.findViewById<TextView>(R.id.headerTextView)?.text.toString()
        titleViewText.split(" ").getOrNull(0)?.trim()?.let { companyName ->
            val formattedCompanyName = companyName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val addressForCompanyName = getAddressForTitle(context, companyName)
            if (addressForCompanyName.isNotEmpty()) {
                stringBuilder.append("$addressForCompanyName\n\n")
            }
            stringBuilder.append("Dear $formattedCompanyName,\n\n")
        }

        // Ensure the reason is valid and not the default spinner prompt
        if (disputeReason == context.getString(R.string.click_to_select_your_reason_for_disputing)) {
            Toast.makeText(context, "Please select a valid reason for disputing.", Toast.LENGTH_SHORT).show()
            return null // or handle appropriately
        }
        // Ensure the reason is valid and not the default spinner prompt
        if (disputeResult == context.getString(R.string.click_to_select_the_result_you_want)) {
            Toast.makeText(context, "Please select a valid reason for disputing.", Toast.LENGTH_SHORT).show()
            return null // or handle appropriately
        }

        // Instantiate LetterGenerator and generate the letter content with names
        val letterGenerator = LetterGenerator(context, radioSelections)
        val letterContent = letterGenerator.generateRandomizedLetter(firstName, lastName, inaccurateName, disputeReason, disputeResult)
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
