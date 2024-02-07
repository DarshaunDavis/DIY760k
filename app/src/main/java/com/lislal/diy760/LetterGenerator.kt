// Package declaration
package com.lislal.diy760

// Import statements
import android.content.Context
import kotlin.random.Random

// Class to generate a letter with randomized sections
class LetterGenerator(private val context: Context, private val radioSelections: Map<Int, String>) {

    // Private function to select a random version of a text section given its resource ID
    private fun getRandomSectionVersion(sectionResourceId: Int): String {
        // Retrieve the array of strings associated with the given resource ID from the application resources
        val versions = context.resources.getStringArray(sectionResourceId)
        // Return a randomly selected version from the array
        return versions[Random.nextInt(versions.size)]
    }

    // Public function to generate the letter with randomized content
    fun generateRandomizedLetter(): String {
        // StringBuilder to build the final letter text
        val stringBuilder = StringBuilder()

        // Sections that are always included regardless of radio selections
        val defaultSectionIds = listOf(
            R.array.section1_versions,
            R.array.section10_versions
        )

        // Append default sections first
        defaultSectionIds.forEach { sectionId ->
            val randomVersion = getRandomSectionVersion(sectionId)
            stringBuilder.append("$randomVersion\n\n")
        }

        // Conditional sections based on radio selections
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

        // Append conditional sections based on radio selections
        conditionalSectionMap.forEach { (radioGroupId, pair) ->
            val selection = radioSelections[radioGroupId]
            if (selection == pair.first) {
                val randomVersion = getRandomSectionVersion(pair.second)
                stringBuilder.append("$randomVersion\n\n")
            }
        }

        // Return the final letter text as a string
        return stringBuilder.toString()
    }
}