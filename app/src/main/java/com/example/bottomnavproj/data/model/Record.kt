package com.example.bottomnavproj.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Record(
    val title: String,
    val distance: String,
    val date: String
) : Parcelable