package com.lislal.diy760

data class PersonalInfoEntry(
    val firstName: String,
    val lastName: String,
    val address: String,
    val apartment: String?,
    val city: String,
    val state: String,
    val zipCode: String,
    val social: String,
    val birthDate: String
)