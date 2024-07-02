package com.dicoding.seasalon.data.network.response

import com.dicoding.seasalon.data.Review
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
	@SerializedName("reviews")
	val reviews: Map<String, Review>
)

