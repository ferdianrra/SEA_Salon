package com.dicoding.seasalon.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val name: String,
    val rating: String,
    val comment: String
) : Parcelable