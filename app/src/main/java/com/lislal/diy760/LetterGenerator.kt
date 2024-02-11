package com.lislal.diy760

import android.content.Context
import kotlin.random.Random

class LetterGenerator(private val context: Context, private val radioSelections: Map<Int, String>) {

    private fun getRandomSectionVersion(sectionResourceId: Int): String {
        val versions = context.resources.getStringArray(sectionResourceId)
        return versions[Random.nextInt(versions.size)]
    }

    fun generateRandomizedLetter(firstName: String = "", lastName: String = "", inaccurateName: String = "", disputeReason: String = "", disputeResult: String = ""): String {
        val stringBuilder = StringBuilder()

        // Always include section1 first
        val section1Id = R.array.section1_versions
        val section1Version = getRandomSectionVersion(section1Id)
        stringBuilder.append("$section1Version\n\n")

        // Conditional sections with customized handling for section2
        val conditionalSectionMap = mapOf(
            R.id.radioGroup1 to Pair("Yes", R.array.section2_versions),
            R.id.radioGroup2 to Pair("Yes", R.array.section3_versions),
            R.id.radioGroup3 to Pair("Yes", R.array.section4_versions),
            R.id.radioGroup4 to Pair("Yes", R.array.section5_versions),
            R.id.radioGroup5 to Pair("Yes", R.array.section6_versions),
            R.id.radioGroup6 to Pair("Yes", R.array.section7_versions),
            R.id.radioGroup7 to Pair("Yes", R.array.section8_versions),
            R.id.radioGroup8 to Pair("Yes", R.array.section11_versions),
            R.id.radioGroup9 to Pair("Yes", R.array.section9_versions)
            // Add more conditional mappings as needed
        )

        conditionalSectionMap.forEach { (radioGroupId, pair) ->
            val selection = radioSelections[radioGroupId]
            if (selection == pair.first) {
                if (radioGroupId == R.id.radioGroup1 && inaccurateName.isNotEmpty() && disputeReason.isNotEmpty()) {
                    // Append customized section2 version
                    stringBuilder.append("Inaccurate Name: $inaccurateName\n" +
                            "Dispute Reason: $disputeReason\n" +
                            "Desired Result: $disputeResult\n\n")
                } else {
                    // Append regular version for other sections
                    val randomVersion = getRandomSectionVersion(pair.second)
                    stringBuilder.append("$randomVersion\n\n")
                }
            }
        }

        // Always include section10 last
        val section10Id = R.array.section10_versions
        val section10Version = getRandomSectionVersion(section10Id)
        val personalizedClosing = if (firstName.isNotEmpty() || lastName.isNotEmpty()) {
            "$section10Version\n\n$firstName $lastName"
        } else {
            section10Version
        }
        stringBuilder.append("$personalizedClosing\n\n")

        return stringBuilder.toString()
    }
}