package com.example.counterapp.data

import com.example.counterapp.api.RemoteApiService
import javax.inject.Inject

class RemoteDataRepository @Inject constructor(
    private val remoteApiService: RemoteApiService
) : DataRepository {
    override suspend fun saveManualCounterData(counterData: CounterData): CounterData {
        return remoteApiService.saveManualCounterData(counterData = counterData)
    }

    override suspend fun fetchSavedManualCounterData(): List<CounterData> {
        return remoteApiService.fetchCounterDataList()
    }
}