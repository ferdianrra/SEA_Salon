package com.dicoding.seasalon.ui.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.seasalon.data.network.response.ErrorResponse
import com.dicoding.seasalon.data.network.response.SendHistoryResponse
import com.dicoding.seasalon.data.network.response.SendReviewResponse
import com.dicoding.seasalon.data.network.retrofit.ApiConfig
import com.dicoding.seasalon.ui.formfeedback.FeedbackViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceViewModel: ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    fun sendHistory(date: String, icon:String, time: String, type:String, name: String, userId: String) {
        val body = mapOf(
            "date" to date,
            "icon" to icon,
            "name" to name,
            "time" to time,
            "type" to type,
            "userid" to userId
        )
        val client = ApiConfig.getApiService().sendHistory(body)
        client.enqueue(object : Callback<SendHistoryResponse> {
            override fun onResponse(
                call: Call<SendHistoryResponse>,
                response: Response<SendHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    val gson = Gson()
                    _isSuccess.value = false
                    try {
                        val errorJson = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SendHistoryResponse>, t: Throwable) {
                _isSuccess.value = false
                Log.e(TAG, "Onfailure: ${t.message}")
            }

        })
    }

    companion object {
        const val TAG = "ServiceViewModel"
    }
}