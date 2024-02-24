package com.lislal.diy760

import android.content.Context
import kotlin.random.Random

class LetterGenerator(
    private val context: Context,
    private val personalInfo: PersonalInfoEntry,
    private val radioSelections: Map<Int, String>,
    private val nameDisputeEntries: List<NameDisputeEntry>
) {

    private fun getRandomSectionVersion(sectionResourceId: Int): String {
        val versions = context.resources.getStringArray(sectionResourceId)
        return versions[Random.nextInt(versions.size)]
    }

    fun generateRandomizedLetter(): String {
        val stringBuilder = StringBuilder()

        // Append user details first
        stringBuilder.apply {
            append("Name: ${personalInfo.firstName} ${personalInfo.lastName}\n")
            append("Address: ${personalInfo.address}, ${personalInfo.city}, ${personalInfo.state} ${personalInfo.zipCode}\n")
            append("SSN: ${personalInfo.social}\n")
            append("Birth Date: ${personalInfo.birthDate}\n\n")
        }

        // Always include section1 first
        stringBuilder.append("${getRandomSectionVersion(R.array.section1_versions)}\n\n")

        // Conditional sections with customized handling
        val conditionalSectionMap = linkedMapOf<Int, Pair<String, Int>>(
            R.id.radioGroup1 to Pair("Yes", R.array.section2_versions),
            R.id.radioGroup2 to Pair("Yes", R.array.section3_versions),
            R.id.radioGroup3 to Pair("Yes", R.array.section4_versions),
            R.id.radioGroup4 to Pair("Yes", R.array.section5_versions),
            R.id.radioGroup5 to Pair("Yes", R.array.section6_versions),
            R.id.radioGroup6 to Pair("Yes", R.array.section7_versions),
            R.id.radioGroup7 to Pair("Yes", R.array.section8_versions),
            R.id.radioGroup8 to Pair("Yes", R.array.section11_versions),
            R.id.radioGroup9 to Pair("Yes", R.array.section9_versions)
        )

        // Process conditional sections
        conditionalSectionMap.forEach { (radioGroupId, pair) ->
            val selection = radioSelections[radioGroupId]
            if (selection == pair.first) {
                stringBuilder.append("${getRandomSectionVersion(pair.second)}\n\n")
                // If this is the section after which name disputes should be added
                if (radioGroupId == R.id.radioGroup1) {
                    // Append name dispute sections
                    nameDisputeEntries.forEach { entry ->
                        stringBuilder.append("Inaccurate Name: ${entry.inaccurateName}\n")
                        stringBuilder.append("Dispute Reason: ${entry.disputeReason}\n")
                        stringBuilder.append("Desired Result: ${entry.disputeResult}\n\n")
                    }
                }
            }
        }

        // Always include section10 last
        stringBuilder.append("${getRandomSectionVersion(R.array.section10_versions)}\n\n")

        // Add a personalized closing if names are provided
        stringBuilder.append("Sincerely,\n${personalInfo.firstName} ${personalInfo.lastName}")

        return stringBuilder.toString()
    }
}