package com.dicoding.seasalon.data.network.response

import com.dicoding.seasalon.data.Review
import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("history")
    val history: Map<String, Review>
)
