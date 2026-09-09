package com.example.counterapp.data.inmemory

import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import com.example.counterapp.data.cloud.CloudCounterData

class InMemoryCounterDataRepository(): CounterDataRepository {
    override suspend fun saveCounterData(counterData: CounterData): CounterData {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCounterData(): List<CounterData> {
        TODO("Not yet implemented")
    }
}
