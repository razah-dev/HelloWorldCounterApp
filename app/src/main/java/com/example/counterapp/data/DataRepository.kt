package com.example.counterapp.data

interface DataRepository {
    suspend fun saveManualCounterData(counterData: CounterData): CounterData

    suspend fun fetchSavedManualCounterData(): List<CounterData>
}