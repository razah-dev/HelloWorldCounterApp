package com.example.counterapp.data.cloud

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CloudCounterDataApiService {
    @POST("save_counter_data")
    suspend fun saveManualCounterData(@Body cloudCounterData: CloudCounterData): CloudCounterData

    @GET("fetch_counter_data_list")
    suspend fun fetchCounterDataList(): List<CloudCounterData>

    @GET("fetch_file_ids_for_device")
    suspend fun fetchAllFileIdsDataList(@Query("device_id") deviceId: Int): List<CloudFileData>
}