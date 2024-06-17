package com.example.myapplication.ui.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Riders(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable
