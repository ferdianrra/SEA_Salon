package com.dicoding.seasalon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val date: String,
    val icon: String,
    val name: String,
    val time: String,
    val type: String,
    val userid: String
): Parcelable
