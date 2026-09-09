package com.example.counterapp.data.cloud

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CloudCounterDataApiService {
    @POST("save_counter_data")
    suspend fun saveManualCounterData(@Body cloudCounterData: CloudCounterData): CloudCounterData

    @GET("fetch_counter_data_list")
    suspend fun fetchCounterDataList(): List<CloudCounterData>
}