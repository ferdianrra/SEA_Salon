package com.dicoding.seasalon.data.network.response

import com.google.gson.annotations.SerializedName

data class SendReviewResponse(

	@field:SerializedName("name")
	val name: String
)
