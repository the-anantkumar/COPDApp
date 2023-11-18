package com.example.copdapp

data class Medication(
    val name: String = "",
    val dosage: String = "",
    val time: Long = 0L, // Time in milliseconds for the alarm
    val userId: String = ""
)
