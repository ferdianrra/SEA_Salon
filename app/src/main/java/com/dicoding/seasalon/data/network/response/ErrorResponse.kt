package com.dicoding.seasalon.data.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String
)