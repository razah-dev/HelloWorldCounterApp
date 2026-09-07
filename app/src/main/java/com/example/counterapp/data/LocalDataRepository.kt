package com.example.counterapp.data

class LocalDataRepository() : DataRepository {
    override suspend fun saveManualCounterData(counterData: CounterData): CounterData {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSavedManualCounterData(): List<CounterData> {
        TODO("Not yet implemented")
    }
}