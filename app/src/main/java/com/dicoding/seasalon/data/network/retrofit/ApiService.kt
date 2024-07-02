package com.dicoding.seasalon.data.network.retrofit

import com.dicoding.seasalon.data.Order
import com.dicoding.seasalon.data.Review
import com.dicoding.seasalon.data.network.response.ReviewResponse
import com.dicoding.seasalon.data.network.response.SendHistoryResponse
import com.dicoding.seasalon.data.network.response.SendReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET ("reviews.json")
    fun getReviews(): Call<Map<String, Review>>

    @POST ("reviews.json")
    fun sendReviews(@Body requestBody: Map<String, String>): Call<SendReviewResponse>

    @GET("history.json")
    fun getHistory(): Call <Map<String, Order>>

    @POST("history.json")
    fun sendHistory(@Body requestBody: Map<String, String>): Call<SendHistoryResponse>
}