package com.example.counterapp.data.local

import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import javax.inject.Inject

class LocalCounterDataRepository @Inject constructor(
    private val localCounterDataDAO: LocalCounterDataDAO
): CounterDataRepository {
    override suspend fun saveCounterData(counterData: CounterData): CounterData {
        val localCounterData: LocalCounterData = LocalCounterData(
            username = counterData.username,
            timestamp8601ISOFormat = counterData.timestamp8601ISOFormat,
            counterValue = counterData.counterValue
        )
        localCounterDataDAO.insert(localCounterData)
        val result: LocalCounterData = localCounterDataDAO.fetch()
        return CounterData(
            username = result.username,
            timestamp8601ISOFormat = result.timestamp8601ISOFormat,
            counterValue = result.counterValue
        )
    }

    override suspend fun fetchCounterData(): List<CounterData> {
        val result: List<LocalCounterData> = localCounterDataDAO.fetchAllRows()
        return result.map {
            CounterData(
                it.username,
                it.timestamp8601ISOFormat,
                it.counterValue
            )
        }
    }
}