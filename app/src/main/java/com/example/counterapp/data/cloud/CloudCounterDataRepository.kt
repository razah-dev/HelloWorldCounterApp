package com.example.counterapp.data.cloud

import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import javax.inject.Inject

class CloudCounterDataRepository @Inject constructor(
    private val cloudCounterDataApiService: CloudCounterDataApiService
) : CounterDataRepository {
    override suspend fun saveCounterData(counterData: CounterData): CounterData {
        // Prepare input
        val cloudCounterData: CloudCounterData = CloudCounterData(
            username = counterData.username,
            timestamp8601ISOFormat = counterData.timestamp8601ISOFormat,
            counterValue = counterData.counterValue
        )
        // Make API call
        val result: CloudCounterData =
            cloudCounterDataApiService.saveManualCounterData(
                cloudCounterData = cloudCounterData)
        // Prepare and return output
        return CounterData(
            username = result.username,
            timestamp8601ISOFormat = result.timestamp8601ISOFormat,
            counterValue = result.counterValue
        )
    }

    override suspend fun fetchCounterData(): List<CounterData> {
        // Make API call
        val result: List<CloudCounterData> =
            cloudCounterDataApiService.fetchCounterDataList()
        // Prepare and return output
        return result.map {
            CounterData(
                username = it.username,
                timestamp8601ISOFormat = it.timestamp8601ISOFormat,
                counterValue = it.counterValue
            )
        }
    }

    override suspend fun fetchAllFileIdsDataList(): List<CloudFileData> {
        return cloudCounterDataApiService.fetchAllFileIdsDataList(deviceId = 120)
    }
}
