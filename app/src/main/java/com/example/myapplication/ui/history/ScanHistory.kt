// ScanHistory.kt
package com.example.myapplication.ui.history

data class ScanHistory(
    val id: Int,
    val name: String,
    val gender: String,
    val age: Int,
    val photo: String,
    val probabilities: List<List<Double>>,
    val message: String?,
    val created_at: String
)
