package com.example.counterapp.data

import com.example.counterapp.data.cloud.CloudFileData

interface CounterDataRepository {
    suspend fun saveCounterData(counterData: CounterData): CounterData

    suspend fun fetchCounterData(): List<CounterData>

    suspend fun fetchAllFileIdsDataList(): List<CloudFileData>
}