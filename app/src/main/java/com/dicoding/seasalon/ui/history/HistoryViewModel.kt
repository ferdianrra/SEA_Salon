package com.dicoding.seasalon.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.seasalon.data.Order
import com.dicoding.seasalon.data.Review
import com.dicoding.seasalon.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {

    private val _listOrder = MutableLiveData<Map<String, Order>>()
    val listOrder: LiveData<Map<String, Order>> = _listOrder

    init {
        getHistory()
    }

    fun getHistory() {
        val client = ApiConfig.getApiService().getHistory()
        client.enqueue(object : Callback<Map<String, Order>> {
            override fun onResponse(
                call: Call<Map<String, Order>>,
                response: Response<Map<String, Order>>
            ) {
                if (response.isSuccessful) {
                    val response = response.body()
                    _listOrder.value = response ?: emptyMap()
                } else {
                    Log.e(TAG, "onFailure: $response.message()")
                }
            }

            override fun onFailure(call: Call<Map<String, Order>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "HistoryViewModel"
    }
}