package com.lislal.diy760

import android.content.Context
import kotlin.random.Random

class LetterGenerator(private val context: Context) {

    private fun getRandomSectionVersion(sectionResourceId: Int): String {
        val versions = context.resources.getStringArray(sectionResourceId)
        return versions[Random.nextInt(versions.size)]
    }

    fun generateRandomizedLetter(): String {
        val stringBuilder = StringBuilder()

        // Assuming you have the IDs of your string arrays
        val sectionIds = listOf(R.array.section1_versions, R.array.section2_versions, R.array.section3_versions, R.array.section4_versions, R.array.section5_versions, R.array.section6_versions, R.array.section7_versions, R.array.section8_versions, R.array.section9_versions, R.array.section10_versions, R.array.section11_versions)

        sectionIds.forEach { sectionId ->
            val randomVersion = getRandomSectionVersion(sectionId)
            stringBuilder.append("$randomVersion\n\n")
        }

        return stringBuilder.toString()
    }
}
