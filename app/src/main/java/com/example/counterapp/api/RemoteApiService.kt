package com.example.counterapp.api

import com.example.counterapp.data.CounterData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RemoteApiService {
    @POST("save_counter_data")
    suspend fun saveManualCounterData(@Body counterData: CounterData): CounterData

    @GET("fetch_counter_data_list")
    suspend fun fetchCounterDataList(): List<CounterData>
}